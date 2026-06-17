package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.math.BigDecimal;
@Getter
@Setter
public class DetallePedidoResponse {
    private Integer id;
    private Pedido idpedido;
    private Plato idplato;
    private Integer cantidad;
    private BigDecimal precioUnitario;
}
