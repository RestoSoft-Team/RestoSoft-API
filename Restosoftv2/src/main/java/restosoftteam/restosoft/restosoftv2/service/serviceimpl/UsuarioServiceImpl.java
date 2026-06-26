package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpleadoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.UsuarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.EmpresaUsuarioService;
import restosoftteam.restosoft.restosoftv2.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EmpresaUsuarioRepository empresaUsuarioRepository;

    private final EmpresaUsuarioService empresaUsuarioService;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioCompletoResponse crearEmpleado(
            Integer idEmpresa,
            EmpleadoRequest request) {

        validarCorreoDisponible(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);

        usuario.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        usuario.setEstado("ACTIVO");

        usuario.setFechaCreacion(LocalDate.now());

        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        return empresaUsuarioService.crearEmpleado(
                usuarioGuardado,
                idEmpresa,
                request.getIdRol()
        );

    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioCompletoResponse obtenerEmpleado(
            Integer idEmpresa,
            Integer idUsuario) {

        return empresaUsuarioService.obtenerEmpleado(
                idEmpresa,
                idUsuario
        );

    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioCompletoResponse> listarEmpleados(
            Integer idEmpresa) {

        return empresaUsuarioService.listarEmpleados(
                idEmpresa
        );

    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerUsuario(Integer idUsuario) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "El usuario no existe."
                        )
                );

        return usuarioMapper.toResponse(usuario);

    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listarUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .toList();

    }

    @Override
    public UsuarioCompletoResponse actualizarEmpleado(
            Integer idEmpresa,
            Integer idUsuario,
            EmpleadoRequest request) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "El usuario no existe."
                        )
                );

        if (!usuario.getEmail().equals(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {

            throw new RegistroYaExistenteException(
                    "Ya existe un usuario registrado con el correo: "
                            + request.getEmail()
            );

        }

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setFechaNacimiento(request.getFechaNacimiento());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

        }

        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioActualizado =
                usuarioRepository.save(usuario);

        return empresaUsuarioService.actualizarEmpleado(
                usuarioActualizado,
                idEmpresa,
                request.getIdRol()
        );

    }

    @Override
    public void eliminarEmpleado(
            Integer idEmpresa,
            Integer idUsuario) {

        empresaUsuarioService.eliminarEmpleado(
                idEmpresa,
                idUsuario
        );

        long relacionesActivas = empresaUsuarioRepository.countByUsuarioId(idUsuario);

        if (relacionesActivas == 0) {
            usuarioRepository.deleteById(idUsuario);
        }
    }

    private void validarCorreoDisponible(String email) {

        if (usuarioRepository.existsByEmail(email)) {

            throw new RegistroYaExistenteException(
                    "Ya existe un usuario registrado con el correo: " + email
            );

        }

    }

    @Override
    public UsuarioResponse crearCliente(ClienteRequest request) {
        validarCorreoDisponible(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario.setEstado("ACTIVO");
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioGuardado);
    }

}