package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PromocionRequest {
    @NotNull
    private String nombre;
    private String descripcion;
    @NotNull
    private BigDecimal valorDescuento;
    @NotNull
    private Integer idEmpresa;
    @NotNull
    private LocalDate fechaInicio;
    @NotNull
    private LocalDate fechaFin;
    private String estado;
    private List<Integer> idsPlatos;
}