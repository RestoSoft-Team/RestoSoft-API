package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaUsuarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.RolRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.EmpresaUsuarioService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpresaUsuarioServiceImpl implements EmpresaUsuarioService {

    private final EmpresaUsuarioRepository empresaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final RolRepository rolRepository;

    @Override
    public EmpresaUsuarioResponse asignar(EmpresaUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + request.getIdusuario()));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada: " + request.getIdempresa()));

        Rol rol = rolRepository.findById(request.getIdrol())
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado: " + request.getIdrol()));

        EmpresaUsuario eu = new EmpresaUsuario();
        eu.setIdusuario(usuario);
        eu.setIdempresa(empresa);
        eu.setIdrol(rol);

        EmpresaUsuario guardado = empresaUsuarioRepository.save(eu);
        return construirResponse(guardado);
    }

    @Override
    public List<EmpresaUsuarioResponse> listarPorEmpresa(Integer idEmpresa) {
        return empresaUsuarioRepository.findByIdempresaId(idEmpresa)
                .stream()
                .map(this::construirResponse)
                .collect(Collectors.toList());
    }

    private EmpresaUsuarioResponse construirResponse(EmpresaUsuario eu) {
        EmpresaUsuarioResponse response = new EmpresaUsuarioResponse();
        response.setId(eu.getId());
        response.setIdEmpresa(eu.getIdempresa().getId());
        response.setIdUsuario(eu.getIdusuario().getId());
        response.setNombreUsuario(eu.getIdusuario().getNombres() + " " + eu.getIdusuario().getApellidos());
        response.setIdRol(eu.getIdrol().getId());
        response.setNombreRol(eu.getIdrol().getNombre());
        return response;
    }
}