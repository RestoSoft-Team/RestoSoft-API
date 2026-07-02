package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
public class MovimientoInventarioRequest {

    @NotNull(message = "El insumo es obligatorio")
    private Integer idInsumo;

    private Integer idProveedor;

    @NotBlank(message = "El tipo de movimiento es obligatorio (ENTRADA/SALIDA)")
    private String tipoMovimiento;

    @NotNull @Positive(message = "La cantidad debe ser mayor a cero")
    private BigDecimal cantidad;
    private BigDecimal stockAnterior;

    private BigDecimal stockNuevo;

    private BigDecimal costoUnitario;

    private String observacion;

    private Instant fechaMovimiento;


    private Integer idEmpresa;
}