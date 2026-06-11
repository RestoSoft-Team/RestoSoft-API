package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaResponse;
import restosoftteam.restosoft.restosoftv2.model.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    Reserva toEntity(ReservaRequest request);
    ReservaResponse toResponse(Reserva reserva);
}
