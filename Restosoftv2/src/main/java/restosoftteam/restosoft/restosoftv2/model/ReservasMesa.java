package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reservas_mesas", schema = "restosoft_v2")
public class ReservasMesa {
    @EmbeddedId
    private ReservasMesaId id;

    @MapsId("idreserva")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idreserva", nullable = false)
    private Reserva idreserva;

    @MapsId("idmesa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmesa", nullable = false)
    private Mesa idmesa;


}