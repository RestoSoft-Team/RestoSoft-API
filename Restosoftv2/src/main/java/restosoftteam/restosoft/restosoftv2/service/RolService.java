package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.RolRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.RolResponse;

import java.util.List;

public interface RolService {

    RolResponse crearRol(RolRequest request);

    List<RolResponse> listarRoles();

    void eliminarRol(Integer id);
}