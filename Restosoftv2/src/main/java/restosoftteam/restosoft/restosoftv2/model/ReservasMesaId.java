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
public class ReservasMesaId implements Serializable {
    private static final long serialVersionUID = -1306716482062954509L;
    @NotNull
    @Column(name = "idreserva", nullable = false)
    private Integer idreserva;

    @NotNull
    @Column(name = "idmesa", nullable = false)
    private Integer idmesa;


}