package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;

import java.util.List;

public interface InsumoService {
    List<InsumoResponse> listarPorEmpresa(Integer idEmpresa);
    InsumoResponse obtenerPorId(Integer id, Integer idEmpresa);
    InsumoResponse crear(Integer idEmpresa,InsumoRequest request);
    InsumoResponse actualizar(Integer id, Integer idEmpresa,InsumoRequest request);
    void eliminar(Integer id, Integer idEmpresa);
}
