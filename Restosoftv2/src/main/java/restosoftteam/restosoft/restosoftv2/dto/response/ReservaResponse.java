package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter
public class ReservaResponse {
    private Integer id;
    private LocalDate fecha;
    private LocalTime hora;
    private UsuarioResponse usuario;
    private Integer cantidadPersonas;
    private EmpresaResponse empresa;
    private String estado;
}
