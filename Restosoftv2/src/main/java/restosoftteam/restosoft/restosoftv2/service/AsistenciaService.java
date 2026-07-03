package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.AsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.MarcarAsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.AsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.MarcarAsistenciaResponse;

import java.util.List;

public interface AsistenciaService {

    AsistenciaResponse registrarEntrada(AsistenciaRequest request);

    AsistenciaResponse registrarSalida(Integer idAsistencia);

    List<AsistenciaResponse> listarAsistenciasPorUsuario(Integer idUsuario, Integer idEmpresa);

    MarcarAsistenciaResponse marcarPorCodigoBarras(MarcarAsistenciaRequest request);
}