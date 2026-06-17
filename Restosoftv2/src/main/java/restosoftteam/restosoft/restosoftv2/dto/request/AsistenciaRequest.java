package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class AsistenciaRequest {

    @NotNull
    private Integer idusuario;

    @NotNull
    private Integer idempresa;

    private Instant fechaEntrada; // opcional, el sistema la pone automáticamente

    private Instant fechaSalida;  // null si aún no ha salido
}