package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class MovimientoInventarioResponse {

    private Integer id;
    private String tipoMovimiento;
    private BigDecimal cantidad;
    private BigDecimal stockAnterior;
    private BigDecimal StockNuevo;
    private String nombreInsumo;
    private Instant fechaMovimiento;
}
