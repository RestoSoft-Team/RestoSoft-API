package restosoftteam.restosoft.restosoftv2.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PedidoUsuarioRequest {

    @NotNull(message = "El mesero asignado a la mesa, no puede estar vacio")
    private Integer idUsuario;

    @NotNull(message = "Debe asignarse meseros al pedido, no puede estar vacio")
    private Integer idPedido;
}
