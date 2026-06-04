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
public class PromocionesHasPlatoId implements Serializable {
    private static final long serialVersionUID = -353122986632252883L;
    @NotNull
    @Column(name = "promociones_idpromocion", nullable = false)
    private Integer promocionesIdpromocion;

    @NotNull
    @Column(name = "platos_idplato", nullable = false)
    private Integer platosIdplato;


}