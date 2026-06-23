package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.HorarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.HorarioResponse;
import java.util.List;

public interface HorarioService {

    HorarioResponse crearHorario(HorarioRequest request);

    HorarioResponse actualizarHorario(Integer id, HorarioRequest request);

    void eliminarHorario(Integer id);

    List<HorarioResponse> listarHorariosPorUsuario(Integer idUsuario, Integer idEmpresa);
}