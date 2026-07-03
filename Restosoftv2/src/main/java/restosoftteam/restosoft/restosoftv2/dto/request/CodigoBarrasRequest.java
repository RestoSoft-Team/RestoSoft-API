package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodigoBarrasRequest {
    @NotBlank(message = "El código de barras es obligatorio.")
    private String codigoBarras;
}