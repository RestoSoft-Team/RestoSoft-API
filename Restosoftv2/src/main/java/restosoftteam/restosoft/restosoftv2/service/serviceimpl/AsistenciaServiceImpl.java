package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.AsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.MarcarAsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.AsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.MarcarAsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.exception.UsuarioDesactivadoException;
import restosoftteam.restosoft.restosoftv2.mapper.AsistenciaMapper;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.AsistenciaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.AsistenciaService;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final EmpresaUsuarioRepository empresaUsuarioRepository;
    private final AsistenciaMapper asistenciaMapper;

    private static final long SEGUNDOS_ANTI_DUPLICADO = 10;

    // ─────────────────────────────────────────────
    // FLUJO MANUAL (panel admin, asistencia.html) — SIN CAMBIOS
    // ─────────────────────────────────────────────

    @Override
    public AsistenciaResponse registrarEntrada(AsistenciaRequest request) {
        Asistencia asistencia = asistenciaMapper.toEntity(request);

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + request.getIdusuario()));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada: " + request.getIdempresa()));

        asistencia.setIdusuario(usuario);
        asistencia.setIdempresa(empresa);
        asistencia.setFechaEntrada(Instant.now()); // el sistema pone la hora

        return asistenciaMapper.toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    public AsistenciaResponse registrarSalida(Integer idAsistencia) {
        Asistencia asistencia = asistenciaRepository.findById(idAsistencia)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asistencia no encontrada: " + idAsistencia));
        asistencia.setFechaSalida(Instant.now());
        return asistenciaMapper.toResponse(asistenciaRepository.save(asistencia));
    }

    @Override
    public List<AsistenciaResponse> listarAsistenciasPorUsuario(Integer idUsuario, Integer idEmpresa) {
        return asistenciaRepository.findByIdusuarioIdAndIdempresaId(idUsuario, idEmpresa)
                .stream()
                .map(asistenciaMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────────
    // FLUJO NUEVO: LECTOR DE CÓDIGO DE BARRAS (kiosco de entrada)
    // ─────────────────────────────────────────────

    @Override
    public MarcarAsistenciaResponse marcarPorCodigoBarras(MarcarAsistenciaRequest request) {

        // 1. Buscar por código de barras
        Usuario usuario = usuarioRepository.findByCodigoBarras(request.getCodigoBarras())
                .orElseThrow(() -> new RecursoNoEncontradoException("Código no reconocido."));

        // 2. Validar que pertenezca a ESTA empresa (multiempresa real)
        boolean perteneceAEmpresa = empresaUsuarioRepository
                .existsByUsuarioIdAndEmpresaId(usuario.getId(), request.getIdEmpresa());
        if (!perteneceAEmpresa) {
            throw new RecursoNoEncontradoException("Código no reconocido en esta empresa.");
        }

        // 3. Validar estado activo
        if (!"ACTIVO".equalsIgnoreCase(usuario.getEstado())) {
            throw new UsuarioDesactivadoException(
                    "El usuario " + usuario.getNombres() + " está inactivo.");
        }

        // 4. Anti-duplicado
        asistenciaRepository.findFirstByIdusuarioIdAndIdempresaIdOrderByIdDesc(
                usuario.getId(), request.getIdEmpresa()
        ).ifPresent(ultimo -> {
            Instant ultimaMarca = ultimo.getFechaSalida() != null
                    ? ultimo.getFechaSalida()
                    : ultimo.getFechaEntrada();
            long segundos = Duration.between(ultimaMarca, Instant.now()).getSeconds();
            if (segundos < SEGUNDOS_ANTI_DUPLICADO) {
                throw new RegistroYaExistenteException(
                        "Ya se registró un marcaje hace unos segundos, espera un momento.");
            }
        });

        // 5. Toggle entrada/salida
        Optional<Asistencia> abierto = asistenciaRepository
                .findFirstByIdusuarioIdAndIdempresaIdAndFechaSalidaIsNullOrderByFechaEntradaDesc(
                        usuario.getId(), request.getIdEmpresa());

        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada."));

        if (abierto.isPresent()) {
            Asistencia a = abierto.get();
            a.setFechaSalida(Instant.now());
            asistenciaRepository.save(a);
            return new MarcarAsistenciaResponse(
                    a.getId(), usuario.getNombres() + " " + usuario.getApellidos(),
                    "SALIDA", a.getFechaSalida());
        } else {
            Asistencia nueva = new Asistencia();
            nueva.setIdusuario(usuario);
            nueva.setIdempresa(empresa);
            nueva.setFechaEntrada(Instant.now());
            asistenciaRepository.save(nueva);
            return new MarcarAsistenciaResponse(
                    nueva.getId(), usuario.getNombres() + " " + usuario.getApellidos(),
                    "ENTRADA", nueva.getFechaEntrada());
        }
    }
}