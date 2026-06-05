package restosoftteam.restosoft.restosoftv2.dto.request;

import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.math.BigDecimal;

public class DetallePedidoRequest {
    private Pedido idpedido;
    private Plato idplato;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
