package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;

import java.util.List;
import java.util.Map;

public interface PedidoService {
    PedidoResponse findById(Integer id);
    List<PedidoResponse> findAllByEmpresa_Id(Integer id);
    PedidoResponse update(PedidoRequest request, Integer id, Integer idEmpresa);
    PedidoResponse save(PedidoRequest request, Integer idEmpresa, Integer idUsuario);
    void delete(Integer id);
    PedidoResponse findPedidoActivoByMesa(Integer idMesa, Integer idEmpresa);
    void enviarTicketPedido(Integer idPedido, Integer idEmpresa);
    void pagarPedidoDividido(Integer idPedido, List<Map<String, Object>> divisiones, Integer idEmpresa);
    void procesarPago(Integer idPedido, String tipoPago, List<Map<String, Object>> metodosPago, Integer idEmpresa);
    void cancelarPedido(Integer idPedido, String motivo, Integer idEmpresa);
}