package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restosoftteam.restosoft.restosoftv2.dto.request.DetalleCompraRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetalleCompraResponse;
import restosoftteam.restosoft.restosoftv2.model.DetalleCompra;

@Mapper(componentModel = "spring")
public interface DetalleCompraMapper {
    @Mapping(target = "nombreInsumo", source = "insumo.nombre")
    DetalleCompraResponse toResponse(DetalleCompra entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "insumo", ignore = true)
    DetalleCompra toEntity(DetalleCompraRequest request);
}
