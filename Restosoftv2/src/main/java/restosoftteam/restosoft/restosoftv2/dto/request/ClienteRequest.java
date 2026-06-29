package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequest {

    @NotBlank
    @Size(max = 100)
    private String nombres;

    @NotBlank
    @Size(max = 100)
    private String apellidos;

    @NotBlank
    @Email
    @Size(max = 150)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.", groups = ClienteRequest.OnCreate.class)
    @Size(min = 8, max = 255, message = "La contraseña debe tener al menos 8 caracteres.", groups = {ClienteRequest.OnCreate.class, ClienteRequest.OnUpdate.class})
    private String password;

    @NotBlank
    @Size(max = 20)
    private String telefono;

    @NotNull
    private LocalDate fechaNacimiento;

    public interface OnCreate {}
    public interface OnUpdate {}

}