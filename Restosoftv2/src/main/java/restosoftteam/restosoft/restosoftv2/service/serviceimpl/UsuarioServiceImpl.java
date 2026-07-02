package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.ClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpleadoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.UsuarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.RolRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.EmpresaUsuarioService;
import restosoftteam.restosoft.restosoftv2.service.UsuarioService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final RolRepository rolRepository;

    private final EmpresaUsuarioRepository empresaUsuarioRepository;

    private final EmpresaUsuarioService empresaUsuarioService;

    private final UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder;

    private final ArchivoStorageService archivoStorageService;

    @Override
    public UsuarioCompletoResponse crearEmpleado(
            Integer idEmpresa,
            EmpleadoRequest request,
            MultipartFile imagen) {

        validarCorreoDisponible(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);

        if (imagen != null && !imagen.isEmpty()) {

            try {
                String rutaImagen = archivoStorageService.guardarImagen(imagen);
                usuario.setImagenUrl(rutaImagen);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen del empleado", e);
            }

        }


        usuario.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        usuario.setEstado("ACTIVO");

        usuario.setFechaCreacion(LocalDate.now());

        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        return empresaUsuarioService.crearUsuario(
                usuarioGuardado,
                idEmpresa,
                request.getIdRol()
        );

    }

    @Override
    public UsuarioResponse actualizarCliente(
            Integer idEmpresa,
            Integer idUsuario,
            ClienteRequest request,
            MultipartFile nuevaImagen) {

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RecursoNoEncontradoException("El cliente no existe."));

        if (!usuario.getEmail().equals(request.getEmail())
                && usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegistroYaExistenteException("Ya existe un usuario registrado con el correo: " + request.getEmail());
        }

        if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
            archivoStorageService.eliminarImagen(usuario.getImagenUrl());
            try {
                String nuevaRuta = archivoStorageService.guardarImagen(nuevaImagen);
                usuario.setImagenUrl(nuevaRuta);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen del cliente", e);
            }
        } else if (request.getEliminarImagen() != null && request.getEliminarImagen()) {
            archivoStorageService.eliminarImagen(usuario.getImagenUrl());
            usuario.setImagenUrl(null);
        }

        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setFechaNacimiento(request.getFechaNacimiento());

        if (request.getEstado() != null) {
            if (request.getEstado()) {
                usuario.setEstado("ACTIVO");
            } else {
                usuario.setEstado("INACTIVO");
            }
        }

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            usuario.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );

        }

        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toResponse(usuarioActualizado);
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
    public List<UsuarioCompletoResponse> listarClientes(
            Integer idEmpresa) {

        return empresaUsuarioService.listarClientes(
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
            EmpleadoRequest request,
            MultipartFile nuevaImagen) {

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

        if (nuevaImagen != null && !nuevaImagen.isEmpty()) {
            archivoStorageService.eliminarImagen(usuario.getImagenUrl());
            try {
                String nuevaRuta = archivoStorageService.guardarImagen(nuevaImagen);
                usuario.setImagenUrl(nuevaRuta);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la nueva imagen", e);
            }
        } else if (request.getEliminarImagen() != null && request.getEliminarImagen()) {
            archivoStorageService.eliminarImagen(usuario.getImagenUrl());
            usuario.setImagenUrl(null);
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
    public UsuarioResponse crearCliente(
            Integer idEmpresa,
            ClienteRequest request,
            MultipartFile imagen) {

        validarCorreoDisponible(request.getEmail());

        Usuario usuario = usuarioMapper.toEntity(request);

        if (imagen != null && !imagen.isEmpty()) {

            try {
                String rutaImagen = archivoStorageService.guardarImagen(imagen);
                usuario.setImagenUrl(rutaImagen);
            } catch (IOException e) {
                throw new RuntimeException("Error al guardar la imagen del empleado", e);
            }

        }

        usuario.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        usuario.setEstado("ACTIVO");

        usuario.setFechaCreacion(LocalDate.now());

        usuario.setFechaActualizacion(LocalDate.now());

        Usuario usuarioGuardado =
                usuarioRepository.save(usuario);

        Rol rol = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(()-> new RecursoNoEncontradoException("Rol de cliente no existente contacte al administrador."));


        UsuarioCompletoResponse usuarioCompleto = empresaUsuarioService.crearUsuario(
                usuarioGuardado,
                idEmpresa,rol.getId());

        if(usuarioCompleto==null){
            throw new RuntimeException("No se pudo crear el usuario");
        }

        return usuarioMapper.toResponse(usuarioGuardado);

    }

}