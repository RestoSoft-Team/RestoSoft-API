package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;

@Mapper(componentModel = "spring")
public interface PlatoCategoriaMapper {

    @Mapping(target = "idempresa", ignore = true)
    PlatoCategoria toEntity(PlatoCategoriaRequest request);

    @Mapping(source = "idempresa.id", target = "idEmpresa")   // ← Agrega esta línea
    PlatoCategoriaResponse toResponse(PlatoCategoria entity);

    @Mapping(target = "idempresa", ignore = true)
    void updateEntityFromDto(PlatoCategoriaRequest dto, @MappingTarget PlatoCategoria entity);
}