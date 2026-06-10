package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

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
    private Usuario idusuario;
    @Positive(message = "La cantidad de personas de la reserva no puede ser negativa.")
    @NotNull(message = "La cantidad de personas de la reserva no puede estar vacia.")
    private Integer cantidadPersonas;
    @NotNull(message = "El id de empresa de la reserva no puede estar vacio.")
    private Empresa idempresa;
    @NotNull(message = "El estado de la reserva no puede estar vacio.")
    private String estado;
}
