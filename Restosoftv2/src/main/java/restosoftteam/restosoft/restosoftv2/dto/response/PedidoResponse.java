package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class PedidoResponse {
    private Integer id;
    private String observaciones;
    private String estado;
    private Instant fecha;

    private List<String> nombresMeseros;
    private String mesasUnidasInfo;
}