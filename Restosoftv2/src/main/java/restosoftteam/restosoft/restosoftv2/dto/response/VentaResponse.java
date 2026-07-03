package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class VentaResponse {
    private Long idVenta;
    private Double valorTotal;
    private LocalDateTime fecha;
    private String nombreUsuario;
    private Set<String> metodosPago;
    private String estadoPago;
    private String observaciones;
    private Integer idPedido;
}
