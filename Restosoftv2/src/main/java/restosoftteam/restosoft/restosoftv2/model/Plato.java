package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "platos", schema = "restosoft_v2")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idplato", nullable = false)
    private Integer id;

    @Size(max = 100)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Size(max = 500)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "representativo", nullable = false)
    private Boolean representativo;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Size(max = 1000)
    @NotNull
    @Column(name = "imagen_url", nullable = false, length = 1000)
    private String imagenUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idcategoria", nullable = false)
    private PlatoCategoria idcategoria;

    @NotNull
    @Lob
    @Column(name = "estado", nullable = false)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;


}