package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.response.UnidadPreparacionResponse;
import restosoftteam.restosoft.restosoftv2.model.UnidadPreparacion;

@Mapper(componentModel = "spring")
public interface UnidadPreparacionMapper {
    UnidadPreparacionResponse toResponse(UnidadPreparacion unidad);
}