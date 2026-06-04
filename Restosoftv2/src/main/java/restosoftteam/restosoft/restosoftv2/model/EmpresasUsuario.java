package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "empresas_usuarios", schema = "restosoft_v2")
public class EmpresasUsuario {
    @EmbeddedId
    private EmpresasUsuarioId id;

    @MapsId("idempresa")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idempresa", nullable = false)
    private Empresa idempresa;

    @MapsId("idusuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idusuario", nullable = false)
    private Usuario idusuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idrol", nullable = false)
    private Rol idrol;


}