package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpresaRequest {
    @NotNull(message = "El nombre de la empresa no puede estar vacio.")
    private String nombre;
    @NotNull(message = "La descripcion de la empresa no puede estar vacia.")
    private String descripcion;
    @NotNull(message = "La direccion de la empresa no puede estar vacia.")
    private String direccion;
    @NotNull(message = "El telefono de la empresa no puede estar vacio.")
    private String telefono;
    @NotNull(message = "El email de la empresa no puede estar vacio.")
    private String email;
    @NotNull(message = "El estado de la empresa no puede estar vacio.")
    private String estado;
}
