package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;

@Mapper(componentModel = "spring")
public interface DetallePedidoMapper {
    @Mapping(target = "idpedido", ignore = true)
    @Mapping(target = "idplato", ignore = true)
    DetallePedido  toEntity(DetallePedidoRequest request);
    DetallePedidoResponse toResponse(DetallePedido entity);
    @Mapping(target = "idpedido", ignore = true)
    @Mapping(target = "idplato", ignore = true)
    DetallePedidoRequest updateEntityFromDto(DetallePedidoRequest dto, @MappingTarget DetallePedido entity);
}
