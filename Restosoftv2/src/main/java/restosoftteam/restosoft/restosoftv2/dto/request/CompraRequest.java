package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraRequest {
    @NotNull(message = "El proveedor es obligatorio")
    private Integer idProveedor;

    @NotNull(message = "La empresa es obligatoria")
    private Integer idEmpresa;

    @NotNull(message = "El total es obligatorio")
    @Positive(message = "El total debe ser mayor a cero")
    private BigDecimal totalCompra;

    @NotNull(message = "Debe seleccionar un método de pago")
    private Integer idMetodoPago;

    @NotEmpty(message = "La compra debe tener al menos un insumo")
    private List<DetalleCompraRequest> detalles;
}
