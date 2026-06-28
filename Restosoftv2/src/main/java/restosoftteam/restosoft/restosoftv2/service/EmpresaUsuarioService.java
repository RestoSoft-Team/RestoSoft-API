package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.util.List;

public interface EmpresaUsuarioService {

    UsuarioCompletoResponse crearUsuario(
            Usuario usuario,
            Integer idEmpresa,
            Integer idRol
    );

    UsuarioCompletoResponse obtenerEmpleado(
            Integer idEmpresa,
            Integer idUsuario
    );

    List<UsuarioCompletoResponse> listarEmpleados(
            Integer idEmpresa
    );

    List<UsuarioCompletoResponse> listarClientes(
            Integer idEmpresa
    );

    UsuarioCompletoResponse actualizarEmpleado(
            Usuario usuario,
            Integer idEmpresa,
            Integer idRol
    );

    void eliminarEmpleado(
            Integer idEmpresa,
            Integer idUsuario
    );

}