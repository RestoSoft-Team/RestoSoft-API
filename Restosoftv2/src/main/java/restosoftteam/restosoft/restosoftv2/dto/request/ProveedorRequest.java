package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProveedorRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 20, message = "El teléfono es muy largo")
    private String telefono;

    private String direccion;

    @Email(message = "Debe ser un email válido")
    private String email;

    @NotNull(message = "El ID de la empresa es obligatorio")
    private Integer idEmpresa;
}
