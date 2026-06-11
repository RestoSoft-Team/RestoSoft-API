package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;

public interface EstiloInterfazEmpresaService {
    EstiloInterfazEmpresaResponse findByIdEmpresa(Integer id);
    EstiloInterfazEmpresaResponse save(EstiloInterfazEmpresaRequest estilo);
    EstiloInterfazEmpresaResponse update(EstiloInterfazEmpresaRequest estilo, Integer id);
    void delete(Integer id);


}
