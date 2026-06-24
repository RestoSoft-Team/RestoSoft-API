package restosoftteam.restosoft.restosoftv2.service;


import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;

import java.util.List;

public interface MesaService {
    MesaResponse crearMesa (MesaRequest mesaRequest);

    MesaResponse crearMesaConZonaReal(MesaRequest mesaRequest, Integer idZonaReal);

    MesaResponse actualizarMesa (MesaRequest mesaRequest, Integer idMesa);

    void intercambiarNumeroMesa (Integer idMesaA, Integer idMesaB);

    void intercambiarUbicacionMesa(Integer idMesaA, Integer idMesaB);

    List<MesaResponse> obtenerMesasPorEmpresa(Integer idEmpresa);
}
