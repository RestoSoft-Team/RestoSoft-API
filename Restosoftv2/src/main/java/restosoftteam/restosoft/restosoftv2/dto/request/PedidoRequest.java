package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class PedidoRequest {
    private String observaciones;

    @NotNull(message = "El estado de el pedido no puede estar vacio.")
    private String estado;

    @NotNull(message = "La fecha de el pedido no puede estar vacio.")
    private Instant fecha;

    @NotNull(message = "Debe asignar al menos una mesa.")
    private List<Integer> idMesas;

    @NotNull(message = "Debe asignar al menos un mesero.")
    private List<Integer> idUsuarios;

    private List<DetallePedidoRequest> detalles;

}