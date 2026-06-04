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
public class MetodoPagoVentaId implements Serializable {
    private static final long serialVersionUID = 4322247082999103510L;
    @NotNull
    @Column(name = "idmetodo_pago", nullable = false)
    private Integer idmetodoPago;

    @NotNull
    @Column(name = "idventa", nullable = false)
    private Integer idventa;


}