package restosoftteam.restosoft.restosoftv2.service;


import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;

public interface MesaService {
    MesaResponse crearMesa (MesaRequest mesaRequest);

    MesaResponse crearMesaConZonaReal(MesaRequest mesaRequest, Integer idZonaReal);

}
