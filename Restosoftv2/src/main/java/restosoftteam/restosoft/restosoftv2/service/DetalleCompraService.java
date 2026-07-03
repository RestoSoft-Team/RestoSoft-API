package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.response.DetalleCompraResponse;

import java.util.List;

public interface DetalleCompraService {
    List<DetalleCompraResponse> listarPorCompra(Integer idCompra);
}
