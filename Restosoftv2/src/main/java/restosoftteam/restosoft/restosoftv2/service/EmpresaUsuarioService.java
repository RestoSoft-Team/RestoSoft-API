package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaUsuarioResponse;

import java.util.List;

public interface EmpresaUsuarioService {

    EmpresaUsuarioResponse asignar(EmpresaUsuarioRequest request);

    List<EmpresaUsuarioResponse> listarPorEmpresa(Integer idEmpresa);
}