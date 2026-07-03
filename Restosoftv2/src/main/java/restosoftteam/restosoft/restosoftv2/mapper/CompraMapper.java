package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restosoftteam.restosoft.restosoftv2.dto.request.CompraRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.CompraResponse;
import restosoftteam.restosoft.restosoftv2.model.Compra;

@Mapper(componentModel = "spring")
public interface CompraMapper {
    CompraResponse toResponse(Compra entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", expression = "java(java.time.Instant.now())")
    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    Compra toEntity(CompraRequest request);
}
