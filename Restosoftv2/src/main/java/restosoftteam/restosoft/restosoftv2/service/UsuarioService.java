package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.UsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import java.util.List;

public interface UsuarioService {

    UsuarioResponse crearUsuario(UsuarioRequest request);

    UsuarioResponse obtenerUsuarioPorId(Integer id);

    List<UsuarioResponse> listarUsuarios();

    List<UsuarioResponse> listarUsuariosPorEmpresa(Integer idEmpresa);

    UsuarioResponse actualizarUsuario(Integer id, UsuarioRequest request);

    void eliminarUsuario(Integer id);

    List<UsuarioResponse> listarClientes();
}