package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorInsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorInsumoResponse;

import java.util.List;

public interface ProveedorInsumoService {

    ProveedorInsumoResponse crear(ProveedorInsumoRequest request);

    List<ProveedorInsumoResponse> listar();

    ProveedorInsumoResponse actualizar(Integer id, ProveedorInsumoRequest request);

    void eliminar(Integer id);

    List<ProveedorInsumoResponse> listarPorProveedor(Integer idProveedor);

    List<ProveedorInsumoResponse> listarPorInsumo(Integer idInsumo);
}
