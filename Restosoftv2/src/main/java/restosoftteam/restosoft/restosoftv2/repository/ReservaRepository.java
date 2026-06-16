package restosoftteam.restosoft.restosoftv2.repository;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.Reserva;

import java.time.LocalDate;
import java.time.LocalTime;

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

}
