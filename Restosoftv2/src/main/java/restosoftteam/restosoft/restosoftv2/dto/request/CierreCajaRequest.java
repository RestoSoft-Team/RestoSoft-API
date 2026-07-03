package restosoftteam.restosoft.restosoftv2.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CierreCajaRequest {
    private LocalDate fecha;
    private BigDecimal apertura;
    private BigDecimal ingresosEfectivo;
    private BigDecimal ingresosTarjeta;
    private BigDecimal propinas;
    private BigDecimal gastos;
    private BigDecimal balanceFinal;
    private String observaciones;
    private String estado;
    private Integer idUsuario;
    private Integer idEmpresa;
}
