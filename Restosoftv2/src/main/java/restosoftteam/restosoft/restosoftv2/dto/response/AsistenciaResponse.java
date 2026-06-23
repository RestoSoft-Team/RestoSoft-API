package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class AsistenciaResponse {

    private Integer id;
    private Integer idUsuario;
    private Integer idEmpresa;
    private Instant fechaEntrada;
    private Instant fechaSalida;
}