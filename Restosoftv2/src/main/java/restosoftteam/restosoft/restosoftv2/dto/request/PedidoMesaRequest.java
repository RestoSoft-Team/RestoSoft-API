package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import restosoftteam.restosoft.restosoftv2.model.Pedido;

public class PedidoMesaRequest {

    @NotNull(message = "No se puede crear un pedido mesa sin su respectivo pedido")
    private Pedido pedido;
}
