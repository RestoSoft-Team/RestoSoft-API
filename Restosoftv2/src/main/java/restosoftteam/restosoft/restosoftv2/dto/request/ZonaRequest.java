package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ZonaRequest {

    @NotNull(message = "Eñ id de la Zona no puede estar vacio")
    private Integer id;

    private Long idTemporal;

    @NotNull(message = "El nombre de la zona no puede estar vacío")
    private String nombreZona;

    @NotNull(message = "La url de la zona no puede estar vacia")
    private String urlZona;

    @NotNull(message = "El estado de la mesa no puede ser null")
    private String estado;

    @NotNull(message = "El id de la Empresa no puede estar vacio")
    private Integer idEmpresa;

}
