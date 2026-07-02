package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

@Getter
@Setter
public class ProveedorInsumoResponse {

    private Integer id;

    private Integer idProveedor;
    private String nombreProveedor;

    private Integer idInsumo;
    private String nombreInsumo;

    private Integer idEmpresa;
    private String nombreEmpresa;

}
