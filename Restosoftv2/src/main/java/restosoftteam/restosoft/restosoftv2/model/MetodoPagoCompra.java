package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "metodo_pago_compras", schema = "restosoft_v2")
public class MetodoPagoCompra {
    @EmbeddedId
    private MetodoPagoCompraId id;

    @MapsId("idcompra")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcompra", nullable = false)
    private Compra idcompra;

    @MapsId("idmetodoPago")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmetodo_pago", nullable = false)
    private MetodoPago idmetodoPago;


}