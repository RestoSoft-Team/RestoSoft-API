package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import restosoftteam.restosoft.restosoftv2.dto.request.AsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.AsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AsistenciaMapper {

    @Mapping(target = "idusuario", ignore = true)
    @Mapping(target = "idempresa", ignore = true)
    Asistencia toEntity(AsistenciaRequest request);

    @Mapping(source = "idusuario.id", target = "idUsuario")
    @Mapping(source = "idempresa.id", target = "idEmpresa")
    AsistenciaResponse toResponse(Asistencia asistencia);
}