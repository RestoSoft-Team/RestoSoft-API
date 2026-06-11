package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.model.Pedido;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "idmesa", ignore = true)
    @Mapping(target = "idusuario", ignore = true)
    Pedido toEntity(PedidoRequest request);

    PedidoResponse toResponse(Pedido pedido);

    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "idmesa", ignore = true)
    @Mapping(target = "idusuario", ignore = true)
    void updateEntityFromDto(PedidoRequest dto,@MappingTarget Pedido entity);
}
