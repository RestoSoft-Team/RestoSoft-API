package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "movimientos_inventario", schema = "restosoft_v2")
public class MovimientosInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmovimiento", nullable = false)
    private Integer id;

    @NotNull
    @Lob
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;

    @NotNull
    @Column(name = "cantidad", nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Column(name = "stock_anterior", precision = 10, scale = 2)
    private BigDecimal stockAnterior;

    @Column(name = "stock_nuevo", precision = 10, scale = 2)
    private BigDecimal stockNuevo;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "fecha_movimiento", nullable = false)
    private Instant fechaMovimiento;

    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idinsumo", nullable = false)
    private Insumo idinsumo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;


}