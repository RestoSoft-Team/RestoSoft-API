package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;

import java.util.List;

@Getter
@Setter
public class ReservaMesaResponse {
    private Integer id;
    private ReservaResponse reserva;
    private List<Mesa> listaMesas;
}
