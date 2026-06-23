package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.Instant;
@Getter
@Setter
public class PedidoResponse {
    private Integer id;
    private String observaciones;
    private Mesa idmesa;
    private Empresa idempresa;
    private String estado;
    private Usuario idusuario;
    private Instant fecha;
}
