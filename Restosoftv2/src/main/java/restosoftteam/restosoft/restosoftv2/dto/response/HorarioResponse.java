package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HorarioResponse {

    private Integer id;
    private Integer idUsuario;
    private Integer idEmpresa;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private String diaSemana;
}