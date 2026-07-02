package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import restosoftteam.restosoft.restosoftv2.enums.EstadoMesa;

@Data
public class MesaRequest {
    private Integer id;
    @NotNull(message = "El numero de mesa no puede estar vacio.")
    private String numeroMesa;
    @Positive(message = "La disponiblidad de personas de la mesa no puede ser negativa.")
    private Integer disponibilidadPersonas;
    @NotNull(message = "La posicion de la mesa no puede estar vacia")
    private double posicionX;
    @NotNull(message = "La posicion de le mesa no puede estar vacia")
    private double posicionY;
    @NotNull(message = "El tipo de mesa no puede estar vacio")
    private String tipoMesa;
    @NotNull(message = "El estado de mesa no puede estar vacio.")
    private EstadoMesa estado;

    private Integer idempresa;
    @NotNull(message = "El id de Zona no puede estar vacío")
    private Long idZona;
}