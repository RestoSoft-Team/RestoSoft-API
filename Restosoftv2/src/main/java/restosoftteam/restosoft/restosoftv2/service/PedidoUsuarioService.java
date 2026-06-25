package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PedidoUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoUsuarioResponse;
import restosoftteam.restosoft.restosoftv2.model.PedidoUsuario;

public interface PedidoUsuarioService {

    PedidoUsuarioResponse findById(Integer id);
    PedidoUsuarioResponse save(PedidoUsuarioRequest request);
    PedidoUsuarioResponse update(PedidoUsuarioRequest request);


}
