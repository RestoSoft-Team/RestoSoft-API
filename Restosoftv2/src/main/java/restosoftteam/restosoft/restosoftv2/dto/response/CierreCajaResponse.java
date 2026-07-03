package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class CierreCajaResponse {
    private Integer id;
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
    private String nombreUsuario;
    private Integer idEmpresa;
}
