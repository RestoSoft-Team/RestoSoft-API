package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Data;

@Data
public class MesaResponse {
    private String numeroMesa;
    private Integer disponibilidadPersonas;
    private double posicionX;
    private double posicionY;
    private String tipoMesa;
    private String estado;
    private Integer idEmpresa;
    private Integer idZona;
}
