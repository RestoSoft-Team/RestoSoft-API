package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatoCategoriaRequest {
    @NotBlank
    private String nombre;
    @NotNull
    private Integer idEmpresa;
}
