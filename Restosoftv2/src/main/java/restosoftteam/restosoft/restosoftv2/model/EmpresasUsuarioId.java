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
public class EmpresasUsuarioId implements Serializable {
    private static final long serialVersionUID = 6100366165395038716L;
    @NotNull
    @Column(name = "idempresa", nullable = false)
    private Integer idempresa;

    @NotNull
    @Column(name = "idusuario", nullable = false)
    private Integer idusuario;


}