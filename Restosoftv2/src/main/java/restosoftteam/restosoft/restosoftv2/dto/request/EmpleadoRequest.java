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
public class EmpleadoRequest {

    @NotBlank(message = "Los nombres son obligatorios.")
    @Size(max = 100)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios.")
    @Size(max = 100)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio.")
    @Email(message = "El correo no es válido.")
    @Size(max = 150)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria.", groups = OnCreate.class)
    @Size(min = 8, max = 255, message = "La contraseña debe tener al menos 8 caracteres.", groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @NotBlank(message = "El teléfono es obligatorio.")
    @Size(max = 20)
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Debe seleccionar un rol.")
    private Integer idRol;

    private Boolean eliminarImagen = false;

    public interface OnCreate {}
    public interface OnUpdate {}


}