package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.UsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.UsuarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.UsuarioService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EmpresaUsuarioRepository empresaUsuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    public UsuarioResponse crearUsuario(UsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegistroYaExistenteException("Ya existe un usuario con ese email");
        }
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setFechaCreacion(LocalDate.now());
        usuario.setFechaActualizacion(LocalDate.now());
        // IMPORTANTE: en producción encriptar password con BCrypt aquí
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public UsuarioResponse obtenerUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + id));
        return usuarioMapper.toResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> listarClientes(){
        return usuarioRepository.findAllByRolCliente()
                .stream().map(usuarioMapper::toResponse).toList();
    }

    @Override
    public List<UsuarioResponse> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UsuarioResponse> listarUsuariosPorEmpresa(Integer idEmpresa) {
        return empresaUsuarioRepository.findByIdempresaId(idEmpresa)
                .stream()
                .map(eu -> usuarioMapper.toResponse(eu.getIdusuario()))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + id));
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setTelefono(request.getTelefono());
        usuario.setEstado(request.getEstado());
        usuario.setFechaNacimiento(request.getFechaNacimiento());
        usuario.setFechaActualizacion(LocalDate.now());
        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}