package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservaCompletaRequest {
    @Valid
    @NotNull(message = "La reserva no puede estar vacia.")
    private ReservaRequest reserva;
    @NotNull(message = "Las mesas relacionadas no pueden estar vacias.")
    private List<Integer> idsMesa;

}