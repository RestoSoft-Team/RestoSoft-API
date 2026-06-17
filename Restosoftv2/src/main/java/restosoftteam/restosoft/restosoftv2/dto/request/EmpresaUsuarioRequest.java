package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaUsuarioRequest {

    @NotNull
    private Integer idempresa;

    @NotNull
    private Integer idusuario;

    @NotNull
    private Integer idrol;
}