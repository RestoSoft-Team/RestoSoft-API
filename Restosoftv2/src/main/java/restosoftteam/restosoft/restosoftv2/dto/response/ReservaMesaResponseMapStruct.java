package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
@Getter
@Setter
public class ReservaMesaResponseMapStruct {
    private Integer id;
    private ReservaResponse reserva;
    private MesaResponse mesa;
}
