package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponseMapStruct;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

import java.util.HashSet;
import java.util.List;

public interface ReservaMesaService {

    List<ReservaMesaResponseMapStruct> findAllByReservaId(Integer idReserva);

    List<ReservaMesaResponseMapStruct> findAllByMesaId(Integer idMesa);

    ReservaMesaResponse save(Reserva reserva, List<Integer> mesasIds);

    ReservaMesaResponse update(Reserva reserva, List<Integer> mesasIds);

    void deleteByReservaAndMesa(Integer idReserva, Integer idMesa);

    void deleteAllByReserva(Integer idReserva);

    List<ReservaMesaResponseMapStruct> findAllByEmpresaIdDiario(Integer idEmpresa);

    List<ReservaMesaResponseMapStruct> findAllByEmpresaIdGeneral(Integer idEmpresa);

    public boolean cambiaronMesas(Reserva reserva, List<Integer> nuevasMesas);

    List<ReservaMesaResponseMapStruct> findAllByCliente(Integer idUsuario);
}
