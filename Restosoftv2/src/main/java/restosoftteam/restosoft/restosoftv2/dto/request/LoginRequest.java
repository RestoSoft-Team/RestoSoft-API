package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El correo no es válido.")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String password;

}