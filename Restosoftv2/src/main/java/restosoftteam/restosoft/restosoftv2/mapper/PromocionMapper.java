package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.PromocionRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PromocionResponse;
import restosoftteam.restosoft.restosoftv2.model.Promocion;

@Mapper(componentModel = "spring")
public interface PromocionMapper {

    @Mapping(target = "idempresa", ignore = true)
    Promocion toEntity(PromocionRequest request);

    // Ignora idsPlatos porque se asigna manualmente en el servicio
    @Mapping(target = "idsPlatos", ignore = true)
    PromocionResponse toResponse(Promocion entity);

    @Mapping(target = "idempresa", ignore = true)
    void updateEntityFromDto(PromocionRequest dto, @MappingTarget Promocion entity);
}
