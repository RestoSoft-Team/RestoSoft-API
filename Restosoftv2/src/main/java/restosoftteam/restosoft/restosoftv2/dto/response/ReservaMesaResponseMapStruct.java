package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Reserva;
@Getter
@Setter
public class ReservaMesaResponseMapStruct {
    private Integer id;
    private Reserva idreserva;
    private Mesa idmesa;
}
