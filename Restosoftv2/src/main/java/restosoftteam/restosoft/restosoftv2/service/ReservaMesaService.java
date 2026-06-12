package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

import java.util.List;

public interface ReservaMesaService {

    List<ReservaMesaResponse> findAllByReserva_Id(Integer idReserva);

    List<ReservaMesaResponse> findAllByMesa_Id(Integer idMesa);

    ReservaMesaResponse save(ReservaMesaRequest reservaMesa);

    List<ReservaMesaResponse> saveAll(List<ReservaMesaRequest> reservasMesas);

    void deleteByReservaAndMesa(Integer idReserva, Integer idMesa);

    void deleteAllByReserva(Integer idReserva);



}
