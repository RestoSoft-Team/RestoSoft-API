package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class PlatoRequest {
    @NotNull(message = "El nombre del plato no puede estar vacio.")
    private String nombre;

    @NotNull(message = "La descripcion del plato no puede estar vacia.")
    private String descripcion;

    @NotNull(message = "El estado representativo de el plato no puede estar vacio.")
    private Boolean representativo;

    @NotNull(message = "El precio del plato no puede estar vacio.")
    private BigDecimal precio;

    private String imagenUrl;

    @NotNull(message = "La categoria del plato no puede estar vacio.")
    private Integer idcategoria;

    @NotNull(message = "El estado del plato no puede estar vacio.")
    private String estado;

    @NotNull(message = "El id de empresa del plato no puede estar vacia.")
    private Integer idempresa;
}