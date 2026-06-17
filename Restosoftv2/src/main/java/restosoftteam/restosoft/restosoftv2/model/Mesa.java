package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mesas", schema = "restosoft_v2")
public class Mesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idmesa", nullable = false)
    private Integer id;

    @Size(max = 20)
    @NotNull
    @Column(name = "numero_mesa", nullable = false, length = 20)
    private String numeroMesa;

    @NotNull
    @Column(name = "disponibilidad_personas", nullable = false)
    private Integer disponibilidadPersonas;

    @NotNull
    @Column(name = "posicion_x")
    private double posicionX;

    @NotNull
    @Column(name = "posicion_y")
    private double posicionY;

    @NotNull
    @Column(name = "tipo_mesa")
    private String tipoMesa;

    @Column(name = "estado", length = 20)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_zona", nullable = false) // Correcto
    private Zona zona;


}