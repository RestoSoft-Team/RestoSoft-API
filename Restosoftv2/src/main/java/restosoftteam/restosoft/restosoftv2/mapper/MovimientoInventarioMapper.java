package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.model.MovimientoInventario;

@Mapper(componentModel = "spring")
public interface MovimientoInventarioMapper {
    @Mapping(target = "nombreInsumo", source = "insumo.nombre")
    MovimientoInventarioResponse toResponse(MovimientoInventario entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaMovimiento", expression = "java(java.time.Instant.now())")
    @Mapping(target = "insumo", ignore = true)
    MovimientoInventario toEntity(MovimientoInventarioRequest request);
}
