package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "metodo_pago_ventas", schema = "restosoft_v2")
public class MetodoPagoVenta {
    @EmbeddedId
    private MetodoPagoVentaId id;

    @MapsId("idmetodoPago")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idmetodo_pago", nullable = false)
    private MetodoPago idmetodoPago;

    @MapsId("idventa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idventa", nullable = false)
    private Venta idventa;


}