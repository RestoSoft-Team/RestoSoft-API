package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.PedidoUsuario;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class PedidoResponse {
    private Integer id;
    private String observaciones;
    private MesaResponse mesa;
    private EmpresaResponse empresa;
    private String estado;
    private Instant fecha;
    private List<PedidoUsuarioResponse> usuarios;
    //
}
