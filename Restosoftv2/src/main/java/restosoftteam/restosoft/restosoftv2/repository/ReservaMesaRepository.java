package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

import java.util.Collection;
import java.util.List;

public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Integer> {
   List<ReservaMesa> findAllByReserva(Reserva reserva);

   List<ReservaMesa> findAllByMesa(Mesa mesa);

   void deleteByReservaAndMesa(Reserva reserva, Mesa mesa);

   void deleteAllByReserva(Reserva reserva);
}
