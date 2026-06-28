package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoUsuarioResponse;
import restosoftteam.restosoft.restosoftv2.model.PedidoUsuario;

@Mapper(componentModel = "spring")
public interface PedidoUsuarioMapper {

    @Mapping(target = "pedido", ignore = true)
    PedidoUsuario toEntity (PedidoUsuarioRequest request);

    @Mapping(target = "pedido", ignore = true)
    PedidoUsuarioResponse toResponse(PedidoUsuario pedido);

}
