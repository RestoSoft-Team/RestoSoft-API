package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.ZonaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;

public interface ZonaService {
    ZonaResponse crearZona(ZonaRequest zonaRequest);
    ZonaResponse actualizarZona(ZonaRequest zonaRequest);
    ZonaResponse eliminarZona(Integer id, Integer idEmpresa);
}
