package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.model.Plato;

@Mapper
public interface PlatoMapper {
    Plato toEntity(PlatoRequest request);
    PlatoResponse toResponse(Plato plato);
}
