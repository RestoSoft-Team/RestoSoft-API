package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoUsuarioResponse {

    private Integer id;
    private Usuario usuario;
    private Pedido pedido;
}
