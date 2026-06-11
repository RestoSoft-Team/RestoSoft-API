package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.math.BigDecimal;

@Getter
@Setter
public class DetallePedidoRequest {
    @Positive(message = "El id del pedido no puede ser negativo.")
    private Pedido idpedido;
    @Positive(message = "El id del plato no puede ser negativo.")
    private Plato idplato;
    @Positive(message = "La cantidad de platos no puede ser negativa. ")
    private Integer cantidad;
    @Positive(message = "El precio del plato no puede ser negativo.")
    private BigDecimal precioUnitario;
}
