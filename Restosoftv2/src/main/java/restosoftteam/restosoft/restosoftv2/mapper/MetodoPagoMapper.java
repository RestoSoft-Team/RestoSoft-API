package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.response.MetodoPagoResponse;
import restosoftteam.restosoft.restosoftv2.model.MetodoPago;

@Mapper(componentModel = "spring")
public interface MetodoPagoMapper {
    MetodoPagoResponse toResponse(MetodoPago entity);
}
