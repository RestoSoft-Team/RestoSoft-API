package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;
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
            EmpleadoRequest request,
            MultipartFile imagen);

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
            EmpleadoRequest request,
            MultipartFile imagen
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

    public UsuarioResponse crearCliente(
            Integer idEmpresa,
            ClienteRequest request,
            MultipartFile imagen);

    List<UsuarioCompletoResponse> listarClientes(Integer idEmpresa);

    UsuarioResponse actualizarCliente(Integer idEmpresa, Integer idUsuario, ClienteRequest request, MultipartFile imagen);
}