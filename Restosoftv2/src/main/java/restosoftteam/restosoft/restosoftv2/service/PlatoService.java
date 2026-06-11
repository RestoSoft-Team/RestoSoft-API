package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;

import java.util.List;

public interface PlatoService {
    PlatoResponse findById(Integer id);
    List<PlatoResponse> findAllByEmpresaId(Integer idEmpresa);
    PlatoResponse update(PlatoRequest request, Integer id);
    PlatoResponse save(PlatoRequest request);
    void delete(Integer id);
}
