package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;

@Mapper
public interface DetallePedidoMapper {
    DetallePedido  toEntity(DetallePedidoRequest request);
    DetallePedidoResponse toResponse(DetallePedido entity);
}
