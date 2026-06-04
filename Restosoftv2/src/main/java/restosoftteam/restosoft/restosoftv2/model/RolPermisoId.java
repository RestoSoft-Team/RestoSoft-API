package restosoftteam.restosoft.restosoftv2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class RolPermisoId implements Serializable {
    private static final long serialVersionUID = -6004525294424404622L;
    @NotNull
    @Column(name = "idrol", nullable = false)
    private Integer idrol;

    @NotNull
    @Column(name = "idpermiso", nullable = false)
    private Integer idpermiso;


}