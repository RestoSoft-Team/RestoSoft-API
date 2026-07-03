package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class PromocionResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal valorDescuento;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private List<Integer> idsPlatos;
}