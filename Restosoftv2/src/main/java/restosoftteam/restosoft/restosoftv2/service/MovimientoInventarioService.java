package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;

import java.util.List;

public interface MovimientoInventarioService {
    List<MovimientoInventarioResponse> listar(Integer idEmpresa);
    MovimientoInventarioResponse crear(MovimientoInventarioRequest request, Integer idEmpresa);
}
