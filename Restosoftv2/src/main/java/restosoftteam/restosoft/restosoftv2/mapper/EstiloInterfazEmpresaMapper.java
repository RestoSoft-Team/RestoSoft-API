package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;

@Mapper(componentModel = "spring")
public interface EstiloInterfazEmpresaMapper {

    @Mapping(target = "idempresa", ignore = true)
    EstiloInterfazEmpresa toEntity(EstiloInterfazEmpresaRequest request);

    EstiloInterfazEmpresaResponse toResponse(EstiloInterfazEmpresa entity);

    @Mapping(target = "idempresa", ignore = true)
    void updateEntityFromDto(EstiloInterfazEmpresaRequest dto, @MappingTarget EstiloInterfazEmpresa entity);
}
