package restosoftteam.restosoft.restosoftv2.dto.request;

import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.Instant;

public class PedidoRequest {
    private String observaciones;
    private Mesa idmesa;
    private Empresa idempresa;
    private String estado;
    private Usuario idusuario;
    private Instant fecha;
}
