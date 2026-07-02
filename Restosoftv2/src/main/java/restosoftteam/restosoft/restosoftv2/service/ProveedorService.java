package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;

import java.util.List;

public interface ProveedorService {
    ProveedorResponse crear(ProveedorRequest request, Integer idEmpresa);

    List<ProveedorResponse> listar(Integer idEmpresa);

    ProveedorResponse actualizar(Integer id, ProveedorRequest request, Integer idEmpresa);

    void eliminar(Integer id);
}
