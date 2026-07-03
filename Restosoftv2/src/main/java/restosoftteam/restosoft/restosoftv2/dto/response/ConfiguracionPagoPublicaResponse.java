package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConfiguracionPagoPublicaResponse {
    private String publicKey;
    private String environment;
    private String proveedor;
    private Boolean activo;
}
