package restosoftteam.restosoft.restosoftv2.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.model.Mesa;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MesaMapper {

    Mesa toEntity(MesaRequest mesaRequest);

    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "zona.id", target = "idZona")
    MesaResponse toResponse(Mesa mesa);
}
