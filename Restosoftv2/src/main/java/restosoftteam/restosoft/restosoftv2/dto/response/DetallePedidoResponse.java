package restosoftteam.restosoft.restosoftv2.dto.response;

import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.math.BigDecimal;

public class DetallePedidoResponse {
    private Integer id;
    private Pedido idpedido;
    private Plato idplato;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
