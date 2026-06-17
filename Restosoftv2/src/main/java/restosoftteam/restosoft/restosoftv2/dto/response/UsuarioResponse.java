package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class UsuarioResponse {

    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String telefono;
    private String estado;
    private LocalDate fechaNacimiento;
    private LocalDate fechaCreacion;
    private LocalDate fechaActualizacion;
}