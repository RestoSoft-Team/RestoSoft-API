package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.AsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.AsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.AsistenciaMapper;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.AsistenciaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.AsistenciaService;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final AsistenciaMapper asistenciaMapper;

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
}