package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.dto.response.EstadoReserva;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ReservaRequest {
    @NotNull(message = "La fecha de la reserva no puede estar vacia.")
    @FutureOrPresent
    private LocalDate fecha;
    @NotNull(message = "La hora de la reserva no puede estar vacia.")
    private LocalTime hora;
    @NotNull(message = "El id usuario de la reserva no puede estar vacia.")
    private Integer idusuario;
    @Positive(message = "La cantidad de personas de la reserva no puede ser negativa.")
    @NotNull(message = "La cantidad de personas de la reserva no puede estar vacia.")
    private Integer cantidadPersonas;
    @NotNull(message = "El id de empresa de la reserva no puede estar vacio.")
    private Integer idempresa;
    @NotNull(message = "El estado de la reserva no puede estar vacio.")
    private EstadoReserva estado;
}
