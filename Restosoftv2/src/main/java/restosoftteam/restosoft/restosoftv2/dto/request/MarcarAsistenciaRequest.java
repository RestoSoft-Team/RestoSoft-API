package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarcarAsistenciaRequest {

    @NotBlank(message = "El código de barras es obligatorio.")
    private String codigoBarras;

    @NotNull(message = "La empresa es obligatoria.")
    private Integer idEmpresa;
}