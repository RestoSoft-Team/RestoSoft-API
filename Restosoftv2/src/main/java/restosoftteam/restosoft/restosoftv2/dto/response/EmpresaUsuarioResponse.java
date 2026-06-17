package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaUsuarioResponse {

    private Integer id;
    private Integer idEmpresa;
    private Integer idUsuario;
    private String nombreUsuario;
    private Integer idRol;
    private String nombreRol;
}