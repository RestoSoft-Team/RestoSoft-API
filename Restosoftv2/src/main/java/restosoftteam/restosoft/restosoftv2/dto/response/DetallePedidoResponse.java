package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DetallePedidoResponse {
    private Integer id;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private Integer cantidadPreparada;
    private String estado;

    private Integer idPlato;
    private String nombrePlato;
    private String categoriaPlato;
}