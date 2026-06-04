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
public class MetodoPagoCompraId implements Serializable {
    private static final long serialVersionUID = 2311926510830979875L;
    @NotNull
    @Column(name = "idcompra", nullable = false)
    private Integer idcompra;

    @NotNull
    @Column(name = "idmetodo_pago", nullable = false)
    private Integer idmetodoPago;


}