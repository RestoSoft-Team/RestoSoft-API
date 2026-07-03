package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import java.util.List;

public interface PlatoService {
    PlatoResponse findById(Integer id);
    List<PlatoResponse> findAllByEmpresaId(Integer idEmpresa);
    PlatoResponse update(PlatoRequest request, Integer id, MultipartFile imagen, Integer idEmpresa);
    PlatoResponse save(PlatoRequest request, Integer idEmpresa, MultipartFile imagen);
    void delete(Integer id);
}