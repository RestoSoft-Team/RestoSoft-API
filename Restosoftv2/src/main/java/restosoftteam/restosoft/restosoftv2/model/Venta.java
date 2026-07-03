package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ventas", schema = "restosoft_v2")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idventa", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "valor_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotal;

    @Column(name = "propina", precision = 12, scale = 2)
    private BigDecimal propina;

    @Size(max = 255)
    @Column(name = "observaciones")
    private String observaciones;

    @Size(max = 50)
    @Column(name = "estado_pago", length = 50)
    private String estadoPago;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idpedido", nullable = false)
    private Pedido idpedido;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario idusuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;

    @ManyToMany
    @JoinTable(
            name = "metodo_pago_ventas",
            schema = "restosoft_v2",
            joinColumns = @JoinColumn(name = "idventa"),
            inverseJoinColumns = @JoinColumn(name = "idmetodo_pago")
    )
    private Set<MetodoPago> metodosPago;
}