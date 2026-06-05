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

    @Size(max = 100)
    @Column(name = "numero_ubicacion", length = 100)
    private String numeroUbicacion;

    @Lob
    @Column(name = "estado")
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;


}