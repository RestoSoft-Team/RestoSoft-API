package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;



@Data
@NoArgsConstructor
@AllArgsConstructor

public class    InsumoRequest {
    private Integer id;

    @NotBlank(message = "El nombre del insumo no puede estar vacío.")
    private String nombre;

    @NotBlank(message = "La unidad de medida no puede estar vacía.")
    private String unidadMedida;

    @NotNull(message = "El stock actual no puede estar vacío.")
    @PositiveOrZero(message = "El stock actual debe ser cero o un valor positivo.")
    private BigDecimal stockActual;

    private BigDecimal stockMinimo;

    @NotNull(message = "El costo unitario no puede estar vacío.")
    @Positive(message = "El costo unitario debe ser mayor a cero.")
    private BigDecimal costoUnitario;

    @NotBlank(message = "El estado de el insumo no puede estar vacío.")
    private String estado;

    @NotNull(message = "El id del proveedor no puede estar vacío.")
    private Integer idProveedor;

    @NotNull(message = "El id de la empresa no puede estar vacío.")
    private Integer idEmpresa;

    private Integer idCategoria;

    private String descripcion;

    private LocalDate fechaVencimiento;
}
