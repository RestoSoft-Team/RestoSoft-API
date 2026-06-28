package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.enums.EstadoMesa;
import restosoftteam.restosoft.restosoftv2.enums.EstadoReserva;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaMesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ReservaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanificadorReservasServiceImpl {

    private final ReservaRepository reservaRepository;
    private final ReservaMesaRepository reservaMesaRepository;
    private final MesaRepository mesaRepository;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void verificarYActivarReservas() {

        List<Reserva> reservasParaActivar = reservaRepository.findReservasPorActivar(LocalDate.now(), LocalTime.now().minusHours(1), EstadoReserva.CONFIRMADA);

        if (reservasParaActivar.isEmpty()) {
            return;
        }


        for (Reserva reserva : reservasParaActivar) {
            List<ReservaMesa> detallesMesa = reservaMesaRepository.findAllByReserva(reserva);

            for (ReservaMesa relacion : detallesMesa) {
                var mesa = relacion.getMesa();

                mesa.setEstado(EstadoMesa.RESERVADA);
                mesaRepository.save(mesa);
            }

            reserva.setEstado(EstadoReserva.ACTIVA);
            reservaRepository.save(reserva);
        }
    }

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void verificarMesasPorCancelar() {

        List<Reserva> reservasParaCancelar = reservaRepository.findReservasPorCancelar(LocalDate.now(), LocalTime.now());

        if (reservasParaCancelar.isEmpty()) {
            return;
        }
        reservasParaCancelar.forEach(reserva -> {reserva.setEstado(EstadoReserva.CANCELADA);});
        reservaRepository.saveAll(reservasParaCancelar);

    }
}
