package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles_permisos", schema = "restosoft_v2")
public class RolPermiso {
    @EmbeddedId
    private RolPermisoId id;

    @MapsId("idrol")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idrol", nullable = false)
    private Rol idrol;

    @MapsId("idpermiso")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idpermiso", nullable = false)
    private Permiso idpermiso;


}