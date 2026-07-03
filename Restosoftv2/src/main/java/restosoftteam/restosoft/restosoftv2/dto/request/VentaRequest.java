package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class VentaRequest {
    @NotNull @Positive
    private Double valorTotal;
    private String observaciones;
    @NotNull
    private Integer idPedido;
    @NotNull
    private Integer idUsuario;
    @NotNull
    private Integer idEmpresa;
    @NotNull
    private Set<Integer> idMetodosPago;
    private LocalDateTime fecha;
}