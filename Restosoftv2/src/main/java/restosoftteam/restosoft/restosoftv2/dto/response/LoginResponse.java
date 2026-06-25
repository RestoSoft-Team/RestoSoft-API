package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private String tipo;

    private Integer usuarioId;

    private Integer empresaId;

    private String nombre;

    private String rol;

}