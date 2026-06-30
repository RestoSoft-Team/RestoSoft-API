package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;

import java.util.List;

public interface PedidoService {
    PedidoResponse findById(Integer id);
    List<PedidoResponse> findAllByEmpresa_Id(Integer id);
    PedidoResponse update(PedidoRequest request, Integer id, Integer idEmpresa);
    PedidoResponse save(PedidoRequest request, Integer idEmpresa, Integer idUsuario);
    void delete(Integer id);
    PedidoResponse findPedidoActivoByMesa(Integer idMesa, Integer idEmpresa);
}