package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

@Mapper(componentModel = "spring")
public interface EmpresaMapper {
    Empresa toEntity(EmpresaRequest request);
    EmpresaResponse toResponse(Empresa entity);
    void  updateEntityFromDto(EmpresaRequest dto, @MappingTarget Empresa entity);
}
