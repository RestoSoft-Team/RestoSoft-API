package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.PromocionRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PromocionResponse;
import java.util.List;

public interface PromocionService {
    PromocionResponse save(PromocionRequest request);
    PromocionResponse update(Integer id, PromocionRequest request);
    void delete(Integer id);
    PromocionResponse findById(Integer id);
    List<PromocionResponse> findAllByEmpresa(Integer idEmpresa);
    List<PromocionResponse> findVigentes();
}