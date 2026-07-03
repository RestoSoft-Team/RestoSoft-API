package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cierres_caja", schema = "restosoft_v2")
public class CierreCaja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cierre", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @NotNull
    @Column(name = "apertura", nullable = false, precision = 12, scale = 2)
    private BigDecimal apertura;

    @NotNull
    @Column(name = "ingresos_efectivo", nullable = false, precision = 12, scale = 2)
    private BigDecimal ingresosEfectivo;

    @NotNull
    @Column(name = "ingresos_tarjeta", nullable = false, precision = 12, scale = 2)
    private BigDecimal ingresosTarjeta;

    @NotNull
    @Column(name = "propinas", nullable = false, precision = 12, scale = 2)
    private BigDecimal propinas;

    @NotNull
    @Column(name = "gastos", nullable = false, precision = 12, scale = 2)
    private BigDecimal gastos;

    @NotNull
    @Column(name = "balance_final", nullable = false, precision = 12, scale = 2)
    private BigDecimal balanceFinal;

    @Column(name = "observaciones")
    private String observaciones;

    @NotNull
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;
}
