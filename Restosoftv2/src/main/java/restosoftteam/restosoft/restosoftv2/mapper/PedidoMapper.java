package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    Pedido toEntity(PedidoRequest request);
    PedidoResponse toResponse(Pedido pedido);
}
