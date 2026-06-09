package restosoftteam.restosoft.restosoftv2.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import restosoftteam.restosoft.restosoftv2.dto.request.ZonaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;
import restosoftteam.restosoft.restosoftv2.model.Zona;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ZonaMapper {

    @Mapping(target = "idEmpresa", ignore = true)
    Zona toEntity(ZonaRequest zonaRequest);

    @Mapping(source = "idEmpresa.id", target = "idEmpresa")
    ZonaResponse toResponse(Zona zona);
}
