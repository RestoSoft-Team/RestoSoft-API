package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.enums.EstadoReserva;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
import restosoftteam.restosoft.restosoftv2.model.ReservaMesa;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT rm " +
            "FROM ReservaMesa rm " +
            "JOIN FETCH rm.reserva r " +
            "JOIN FETCH rm.mesa m " +
            "WHERE r.fecha = :fecha " +
            "AND r.hora BETWEEN :horaInicio AND :horaFin " +
            "AND (:idReserva IS NULL OR r.id <> :idReserva) " +
            "AND r.estado <> 'CANCELADA' " +
            "AND r.estado <> 'FINALIZADA'")
    List<ReservaMesa> buscarMesasReservadas(
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("idReserva") Integer idReserva
    );

    @Query("SELECT r FROM Reserva r WHERE r.fecha = :hoy AND r.hora <= :ahora AND r.estado = :estado")
    List<Reserva> findReservasPorActivar(
            @Param("hoy") java.time.LocalDate hoy,
            @Param("ahora") java.time.LocalTime ahora,
            @Param("estado") EstadoReserva estado
    );

    @Modifying
    @Transactional
    @Query("UPDATE Reserva r SET r.estado = :estadoReserva WHERE r.id = :id")
    Boolean updateEstadoById(@Param("id") Integer id, @Param("estadoReserva") EstadoReserva estadoReserva);

    @Query("SELECT r FROM Reserva r WHERE r.fecha = :hoy AND r.hora <= :ahora AND r.estado = 'PENDIENTE'")
    List<Reserva> findReservasPorCancelar(@Param("hoy") LocalDate hoy, @Param("ahora") LocalTime ahora);

}
