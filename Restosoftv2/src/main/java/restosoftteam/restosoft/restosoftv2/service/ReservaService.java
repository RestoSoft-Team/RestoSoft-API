package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaResponse;

import java.util.List;

public interface ReservaService {
    ReservaMesaResponse save(ReservaRequest request, List<Integer> idSmesas);
    ReservaResponse update(ReservaRequest request, List<Integer> idSmesas, Integer idReserva);
    void delete(Integer idReserva);


}
