package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movimientos_inventario", schema = "restosoft_v2")
public class MovimientoInventario {
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
    @Column(name = "fecha_movimiento", nullable = false, columnDefinition = "DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6)")
    private Instant fechaMovimiento;

    @Size(max = 255)
    @Column(name = "observacion")
    private String observacion;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idinsumo", nullable = false)
    private Insumo insumo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;


}