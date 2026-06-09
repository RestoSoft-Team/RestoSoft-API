package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.List;

@Data
public class ConfiguracionesRestauranteRequest {

    @NotNull
    private Integer idEmpresa;

    @Valid
    @NotNull
    private List<ZonaRequest> zonas;

    @Valid
    @NotNull
    private List<MesaRequest> mesas;
}