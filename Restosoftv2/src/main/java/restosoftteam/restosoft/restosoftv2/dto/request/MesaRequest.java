package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

public class MesaRequest {
    @NotNull(message = "El numero de mesa no puede estar vacio.")
    private String numeroMesa;
    @Positive(message = "La disponiblidad de personas de la mesa no puede ser negativa.")
    private Integer disponibilidadPersonas;
    @NotNull(message = "El numeroUbicacion de  la mesa no puede estar vacio.")
    private String numeroUbicacion;
    @NotNull(message = "El estado de mesa no puede estar vacio.")
    private String estado;
    @NotNull(message = "El id de empresa no puede estar vacio.")
    private Empresa idempresa;
}
