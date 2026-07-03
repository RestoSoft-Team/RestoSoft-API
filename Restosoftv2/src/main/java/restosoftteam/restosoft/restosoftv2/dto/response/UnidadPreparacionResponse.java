package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
public class UnidadPreparacionResponse {
    private Integer id;
    private String estado;
    private Instant fechaInicio;
    private Instant fechaListo;
}