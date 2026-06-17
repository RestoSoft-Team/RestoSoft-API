package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Data;
import restosoftteam.restosoft.restosoftv2.enums.EstadoMesa;

@Data
public class MesaResponse {
    private String numeroMesa;
    private Integer disponibilidadPersonas;
    private double posicionX;
    private double posicionY;
    private String tipoMesa;
    private EstadoMesa estado;
    private Integer idEmpresa;
    private Integer idZona;
}
