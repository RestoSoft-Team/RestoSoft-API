package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

import java.util.List;

public interface ReservaMesaRepository extends JpaRepository<ReservaMesa, Integer> {
   List<ReservaMesa> findAllByReserva(Reserva reserva);

   List<ReservaMesa> findAllByMesa(Mesa mesa);

   void deleteByReservaAndMesa(Reserva reserva, Mesa mesa);

   void deleteAllByReserva(Reserva reserva);


    @Query("SELECT r FROM ReservaMesa r " +
            "JOIN FETCH r.reserva res " +
            "JOIN FETCH res.usuario u " +
            "JOIN FETCH r.mesa m " +
            "WHERE res.empresa = :empresa AND res.fecha = CURRENT_DATE")
    List<ReservaMesa> findAllByEmpresaAndFechaToday(@Param("empresa") Empresa empresa);

    @Query("SELECT r FROM ReservaMesa r " +
            "JOIN FETCH r.reserva res " +
            "JOIN FETCH res.usuario u " +
            "JOIN FETCH r.mesa m " +
            "WHERE res.empresa = :empresa")
    List<ReservaMesa> findAllByEmpresa(@Param("empresa") Empresa empresa);

}
