package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RolRequest {

    @NotBlank(message = "El nombre del rol no puede estar vacio.")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres.")
    private String nombre;
}