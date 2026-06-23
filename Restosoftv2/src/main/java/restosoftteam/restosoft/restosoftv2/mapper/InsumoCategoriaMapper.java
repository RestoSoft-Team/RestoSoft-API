package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.model.InsumoCategoria;

@Mapper(componentModel = "spring")
public interface InsumoCategoriaMapper {

    InsumoCategoria toEntity(InsumoCategoriaRequest request);

    InsumoCategoriaResponse toResponse(InsumoCategoria entity);

    void updateEntity(InsumoCategoriaRequest request, @MappingTarget InsumoCategoria entity);
}
