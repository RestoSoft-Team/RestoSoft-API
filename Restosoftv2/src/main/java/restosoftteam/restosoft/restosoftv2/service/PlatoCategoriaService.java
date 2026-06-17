package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoCategoriaResponse;

import java.util.List;

@Service
public interface PlatoCategoriaService {
    PlatoCategoriaResponse save(PlatoCategoriaRequest request);
    PlatoCategoriaResponse update(Integer id, PlatoCategoriaRequest request);
    void delete(Integer id);
    PlatoCategoriaResponse findById(Integer id);
    List<PlatoCategoriaResponse> findAllByEmpresa(Integer idEmpresa);
}
