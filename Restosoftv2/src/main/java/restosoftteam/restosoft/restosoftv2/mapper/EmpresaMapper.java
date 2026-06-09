package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

@Mapper
public interface EmpresaMapper {
    Empresa toEntity(EmpresaRequest request);
    EmpresaResponse toResponse(Empresa entity);
}
