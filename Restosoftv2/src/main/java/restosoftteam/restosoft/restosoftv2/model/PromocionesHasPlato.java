package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promociones_has_platos", schema = "restosoft_v2")
public class PromocionesHasPlato {
    @EmbeddedId
    private PromocionesHasPlatoId id;

    @MapsId("promocionesIdpromocion")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "promociones_idpromocion", nullable = false)
    private Promocion promocionesIdpromocion;

    @MapsId("platosIdplato")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "platos_idplato", nullable = false)
    private Plato platosIdplato;


}