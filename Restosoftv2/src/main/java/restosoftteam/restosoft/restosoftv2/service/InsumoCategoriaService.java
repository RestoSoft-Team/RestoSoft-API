package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.InsumoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoCategoriaResponse;

import java.util.List;

public interface InsumoCategoriaService {

    List<InsumoCategoriaResponse> listarTodos();

    InsumoCategoriaResponse buscarPorId(Integer id);

    InsumoCategoriaResponse crear(InsumoCategoriaRequest request);

    InsumoCategoriaResponse actualizar(Integer id, InsumoCategoriaRequest request);

    void eliminar(Integer id);
}
