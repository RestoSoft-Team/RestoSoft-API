package restosoftteam.restosoft.restosoftv2.repository;

import org.hibernate.sql.Update;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.enums.EstadoReserva;
import restosoftteam.restosoft.restosoftv2.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.fecha = :fecha AND r.hora BETWEEN :horaInicio AND :horaFin")
    Long buscarPorFechaYRangoHora(
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin
    );

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.fecha = :fecha AND r.hora BETWEEN :horaInicio AND :horaFin AND r.id <> :id")
    Long buscarPorFechaYRangoHoraExcluyendoId(
            @Param("fecha") LocalDate fecha,
            @Param("horaInicio") LocalTime horaInicio,
            @Param("horaFin") LocalTime horaFin,
            @Param("id")  Integer id
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

}
