package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpleadoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;

import java.util.List;

public interface UsuarioService {

    /**
     * Crea un empleado.
     * Crea el usuario y posteriormente asigna
     * la empresa y el rol correspondiente.
     */
    UsuarioCompletoResponse crearEmpleado(
            Integer idEmpresa,
            EmpleadoRequest request
    );

    /**
     * Obtiene un empleado específico
     * perteneciente a una empresa.
     */
    UsuarioCompletoResponse obtenerEmpleado(
            Integer idEmpresa,
            Integer idUsuario
    );

    /**
     * Lista todos los empleados
     * de una empresa.
     */
    List<UsuarioCompletoResponse> listarEmpleados(
            Integer idEmpresa
    );

    /**
     * Actualiza la información
     * del empleado.
     */
    UsuarioCompletoResponse actualizarEmpleado(
            Integer idEmpresa,
            Integer idUsuario,
            EmpleadoRequest request
    );

    /**
     * Elimina un empleado.
     */
    void eliminarEmpleado(
            Integer idEmpresa,
            Integer idUsuario
    );

    /**
     * Obtiene únicamente la información
     * básica de un usuario.
     */
    UsuarioResponse obtenerUsuario(
            Integer idUsuario
    );

    /**
     * Lista únicamente los usuarios.
     */
    List<UsuarioResponse> listarUsuarios();

    UsuarioResponse crearCliente(ClienteRequest request);

}