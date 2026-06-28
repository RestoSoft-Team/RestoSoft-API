package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProveedorResponse {

    private Integer id;
    private String nombre;
    private String telefono;
    private String email;
    private String direccion;
    private String nombreEmpresa;
    private Integer idEmpresa;
}
