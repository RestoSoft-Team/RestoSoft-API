package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservaCompletaRequest {
    @NotNull(message = "La reserva no puede estar vacia.")
    private ReservaRequest reserva;
    @NotNull(message = "Las mesas relacionadas no pueden estar vacias.")
    private List<Integer> idsMesa;

}