package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorInsumoRequest {

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Integer idProveedor;

    @NotNull(message = "El ID del insumo es obligatorio")
    private Integer idInsumo;
}
