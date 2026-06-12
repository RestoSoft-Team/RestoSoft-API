package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;

import java.util.List;

public interface DetallePedidoService {
    List<DetallePedidoResponse> listAllByPedidoId(int idPedido);
    DetallePedidoResponse findByDetallePedidoId(Integer id, Integer idEmpresa);
    DetallePedidoResponse save(DetallePedidoRequest detallePedidoRequest);
    DetallePedidoResponse update(DetallePedidoRequest detallePedidoRequest, Integer id);
    void deleteByid(Integer id);
}
