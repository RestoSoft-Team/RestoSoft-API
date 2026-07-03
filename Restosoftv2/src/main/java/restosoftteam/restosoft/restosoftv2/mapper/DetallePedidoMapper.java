package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UnidadPreparacionResponse;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;
import restosoftteam.restosoft.restosoftv2.model.UnidadPreparacion;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "plato", ignore = true)
    DetallePedido toEntity(DetallePedidoRequest request);

    @Mapping(source = "plato.id", target = "idPlato")
    @Mapping(source = "plato.nombre", target = "nombrePlato")
    @Mapping(source = "plato.categoria.nombre", target = "categoriaPlato")
    DetallePedidoResponse toResponse(DetallePedido entity);

    UnidadPreparacionResponse toResponse(UnidadPreparacion unidad);

    @Mapping(target = "pedido", ignore = true)
    @Mapping(target = "plato", ignore = true)
    void updateEntityFromDto(DetallePedidoRequest dto, @MappingTarget DetallePedido entity);
}