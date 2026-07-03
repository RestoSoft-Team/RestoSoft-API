package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarcarAsistenciaResponse {

    private Integer idAsistencia;
    private String nombreCompleto;
    private String tipoRegistro; // "ENTRADA" o "SALIDA"
    private Instant fecha;
}