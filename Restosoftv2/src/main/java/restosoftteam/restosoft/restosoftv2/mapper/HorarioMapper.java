package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import restosoftteam.restosoft.restosoftv2.dto.request.HorarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.HorarioResponse;
import restosoftteam.restosoft.restosoftv2.model.Horario;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HorarioMapper {

    @Mapping(target = "idusuario", ignore = true)
    @Mapping(target = "idempresa", ignore = true)
    Horario toEntity(HorarioRequest request);

    @Mapping(source = "idusuario.id", target = "idUsuario")
    @Mapping(source = "idempresa.id", target = "idEmpresa")
    HorarioResponse toResponse(Horario horario);
}