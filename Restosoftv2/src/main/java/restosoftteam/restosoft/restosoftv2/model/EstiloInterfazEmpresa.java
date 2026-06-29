package restosoftteam.restosoft.restosoftv2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "primary_color", length = 50)
    private String primary;

    @Size(max = 50)
    @Column(name = "primary_light", length = 50)
    private String primaryLight;

    @Size(max = 50)
    @Column(name = "primary_soft", length = 50)
    private String primarySoft;

    @Size(max = 50)
    @Column(name = "background", length = 50)
    private String background;

    @Size(max = 50)
    @Column(name = "surface", length = 50)
    private String surface;

    @Size(max = 50)
    @Column(name = "surface_container", length = 50)
    private String surfaceContainer;

    @Size(max = 50)
    @Column(name = "border_color", length = 50)
    private String borderColor;

    @Size(max = 50)
    @Column(name = "dark", length = 50)
    private String dark;

    @Size(max = 50)
    @Column(name = "gray", length = 50)
    private String gray;

    @Size(max = 50)
    @Column(name = "soft", length = 50)
    private String soft;

    @Size(max = 50)
    @Column(name = "success", length = 50)
    private String success;

    @Size(max = 50)
    @Column(name = "success_soft", length = 50)
    private String successSoft;

    @Size(max = 50)
    @Column(name = "warning", length = 50)
    private String warning;

    @Size(max = 50)
    @Column(name = "warning_soft", length = 50)
    private String warningSoft;

    @Size(max = 50)
    @Column(name = "danger", length = 50)
    private String danger;

    @Size(max = 50)
    @Column(name = "danger_soft", length = 50)
    private String dangerSoft;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    @JsonIgnore
    private Empresa empresa;
}