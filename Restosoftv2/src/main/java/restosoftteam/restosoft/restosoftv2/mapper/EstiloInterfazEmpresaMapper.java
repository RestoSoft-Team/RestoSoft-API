package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;

@Mapper
public interface EstiloInterfazEmpresaMapper {
    EstiloInterfazEmpresa toEntity(EstiloInterfazEmpresaRequest request);
    EstiloInterfazEmpresaResponse toResponse(EstiloInterfazEmpresa entity);
}
