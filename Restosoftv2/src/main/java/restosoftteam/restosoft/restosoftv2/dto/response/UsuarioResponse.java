package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Rol;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {

    private Integer id;

    private String nombres;

    private String apellidos;

    private String email;

    private String telefono;

    private LocalDate fechaNacimiento;

    private String estado;

    private RolResponse rol;

    private String imagenUrl;

    private  String codigoBarras;

}