package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;

import java.util.List;

public interface ProveedorService {
    ProveedorResponse crear(ProveedorRequest request);
    List<ProveedorResponse> listar();
    ProveedorResponse actualizar(Integer id, ProveedorRequest request); // NUEVO
    void eliminar(Integer id);
}
