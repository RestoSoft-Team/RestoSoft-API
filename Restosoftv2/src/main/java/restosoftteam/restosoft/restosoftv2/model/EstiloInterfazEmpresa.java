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
@Table(name = "estilos_interfaces_empresas", schema = "restosoft_v2")
public class EstiloInterfazEmpresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idestilo", nullable = false)
    private Integer id;

    @Size(max = 50)
    @Column(name = "color_primario", length = 50)
    private String colorPrimario;

    @Size(max = 50)
    @Column(name = "color_secundario", length = 50)
    private String colorSecundario;

    @Size(max = 50)
    @Column(name = "color_botones", length = 50)
    private String colorBotones;

    @Size(max = 50)
    @Column(name = "color_enfasis_tablas", length = 50)
    private String colorEnfasisTablas;

    @Size(max = 50)
    @Column(name = "color_sidebar", length = 50)
    private String colorSidebar;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa empresa;


}