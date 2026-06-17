package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalTime;

@Getter
@Setter
public class HorarioRequest {

    @NotNull
    private Integer idusuario;

    @NotNull
    private Integer idempresa;

    @NotNull
    private LocalTime horaEntrada;

    @NotNull
    private LocalTime horaSalida;

    @NotNull
    private String diaSemana; // "LUNES"

}