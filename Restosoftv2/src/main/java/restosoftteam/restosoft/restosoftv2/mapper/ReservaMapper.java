package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaResponse;
import restosoftteam.restosoft.restosoftv2.model.Reserva;

@Mapper(componentModel = "spring")
public interface ReservaMapper {
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    Reserva toEntity(ReservaRequest request);

    ReservaResponse toResponse(Reserva reserva);

    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "empresa", ignore = true)
    void updateReservaFromDto(ReservaRequest request, @MappingTarget Reserva reserva);
}
