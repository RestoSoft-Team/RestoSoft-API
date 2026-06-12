package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.ReservaMesaMapper;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaMesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaRepository;
import restosoftteam.restosoft.restosoftv2.service.ReservaMesaService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReservaMesaServiceImpl implements ReservaMesaService {
    private final ReservaMesaRepository reservaMesaRepository;
    private final ReservaMesaMapper reservaMesaMapper;
    private final ReservaRepository reservaRepository;
    private final MesaRepository mesaRepository;

    @Override
    public List<ReservaMesaResponse> findAllByReserva_Id(Integer idReserva) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));


        return reservaMesaRepository.findAllByReserva(reserva)
                .stream().map(reservaMesaMapper::toResponse).toList();

    }

    @Override
    public List<ReservaMesaResponse> findAllByMesa_Id(Integer idMesa) {

        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada no existe."));

        return reservaMesaRepository.findAllByMesa(mesa)
                .stream().map(reservaMesaMapper::toResponse).toList();

    }

    @Override
    @Transactional
    public ReservaMesaResponse save(ReservaMesaRequest request) {

        if (!reservaRepository.existsById(request.getIdreserva())) {
            throw new RecursoNoEncontradoException("La reserva relacionada no existe.");
        }

        if (!mesaRepository.existsById(request.getIdmesa())) {
            throw new RecursoNoEncontradoException("La mesa relacionada no existe.");
        }

        return reservaMesaMapper.toResponse(
                reservaMesaRepository.save(toEntityuseGetReference(request)));

    }


    @Override
    @Transactional
    public List<ReservaMesaResponse> saveAll(List<ReservaMesaRequest> reservasMesas) {

        reservasMesas.forEach(request ->
                validarExistencia(request.getIdreserva(), request.getIdmesa()));

        List<ReservaMesa> reservasMesa = reservasMesas.stream()
                .map(this::toEntityuseGetReference).toList();


        return reservaMesaRepository.saveAll(reservasMesa)
                .stream().map(reservaMesaMapper::toResponse).toList();


    }

    @Override
    @Transactional
    public void deleteByReservaAndMesa(Integer idReserva, Integer idMesa) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));

        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada no existe."));

        reservaMesaRepository.deleteByReservaAndMesa(reserva, mesa);

    }

    @Override
    @Transactional
    public void deleteAllByReserva(Integer idReserva) {

        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("La reserva relacionada no existe."));
        reservaMesaRepository.deleteAllByReserva(reserva);

    }

    private void validarExistencia(Integer idReserva, Integer idMesa) {
        if (!reservaRepository.existsById(idReserva)) {
            throw new RecursoNoEncontradoException("La reserva relacionada con ID " + idReserva + " no existe.");
        }
        if (!mesaRepository.existsById(idMesa)) {
            throw new RecursoNoEncontradoException("La mesa relacionada con ID " + idMesa + " no existe.");
        }
    }

    private ReservaMesa toEntityuseGetReference(ReservaMesaRequest request) {

        ReservaMesa reservaMesa = new ReservaMesa();
        reservaMesa.setMesa(mesaRepository.getReferenceById(request.getIdmesa()));
        reservaMesa.setReserva(reservaRepository.getReferenceById(request.getIdreserva()));

        return reservaMesa;

    }



    /// FALTA AÑADIR QUE UNA RESERVA SEA LA QUE CONTENGA TODAS LAS MESAS NO VARIAS Y QUE LO DE FECHAS PARA ACTIVACION

}
