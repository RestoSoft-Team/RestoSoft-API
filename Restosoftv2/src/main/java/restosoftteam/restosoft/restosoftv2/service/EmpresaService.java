package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;

import java.util.List;

public interface EmpresaService {
    EmpresaResponse findById(Integer id);
    List<EmpresaResponse> findAll();
    EmpresaResponse save(EmpresaRequest empresaRequest);
    EmpresaResponse update(EmpresaRequest request, Integer id);
    void delete(Integer id);
}
