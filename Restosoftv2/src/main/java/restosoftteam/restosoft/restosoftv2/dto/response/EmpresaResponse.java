package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String telefono;
    private String email;
    private String estado;
}
