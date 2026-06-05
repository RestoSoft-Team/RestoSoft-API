package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.Instant;

public class PedidoRequest {

    private String observaciones;
    @NotNull(message = "El id mesa no puede estar vacio.")
    private Mesa idmesa;
    @NotNull(message = "El id empresa no puede estar vacio.")
    private Empresa idempresa;
    @NotNull(message = "El estado de el pedido no puede estar vacio.")
    private String estado;
    @NotNull(message = "El id usuario de el pedido no puede estar vacio.")
    private Usuario idusuario;
    @NotNull(message = "La fecha de el pedido no puede estar vacio.")
    private Instant fecha;
}
