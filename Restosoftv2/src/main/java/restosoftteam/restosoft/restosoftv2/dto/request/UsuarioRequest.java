package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


@Getter
@Setter

public class UsuarioRequest {
    @NotBlank(message = "El nombre no puede estar vacio.")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres.")
    private String nombres;

    @NotBlank(message = "El apellido no puede estar vacio.")
    @Size(max = 100, message = "El apellido no puede superar 100 caracteres.")
    private String apellidos;

    @NotBlank(message = "El email no puede estar vacio.")
    @Email(message = "El email no tiene un formato valido.")
    @Size(max = 150, message = "El email no puede superar 150 caracteres.")
    private String email;

    @Size(max = 20, message = "El telefono no puede superar 20 caracteres.")
    private String telefono;

    @NotBlank(message = "La contraseña no puede estar vacia.")
    @Size(min = 6, max = 255, message = "La contraseña debe tener minimo 6 caracteres.")
    private String password;

    private LocalDate fechaNacimiento;

    @NotBlank(message = "El estado no puede estar vacio.")
    private String estado;

}
