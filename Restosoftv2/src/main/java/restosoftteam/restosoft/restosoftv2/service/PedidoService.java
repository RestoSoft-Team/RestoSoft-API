package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;

import java.util.List;

public interface PedidoService {
    PedidoResponse findById(Integer id);
    List<PedidoResponse> findAllByIdEmpresa_Id(Integer id);
    PedidoResponse update(PedidoRequest request, Integer id);
    PedidoResponse save(PedidoRequest request);
    void delete(Integer id);
}
