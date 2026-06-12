package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.model.Plato;

@Mapper(componentModel = "spring")
public interface PlatoMapper {
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Plato toEntity(PlatoRequest request);

    PlatoResponse toResponse(Plato plato);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntityFromDto(PlatoRequest dto, @MappingTarget Plato entity);
}
