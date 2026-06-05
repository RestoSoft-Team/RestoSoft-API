package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

public class ConfiguracionPaginaClienteRequest {

    @NotNull(message = "El titulo de la pagina cliente no puede estar vacio.")
    private String titulo;
    @NotNull(message = "El lema de la pagina cliente no puede estar vacio.")
    private String lema;
    @NotNull(message = "El logo de la pagina cliente no puede estar vacio.")
    private String logo;
    @NotNull(message = "La direccion en la pagina cliente no puede estar vacio.")
    private String direccion;
    @NotNull(message = "Los horarios de atencion de la pagina cliente no pueden estar vacios.")
    private String horarios;
    @NotNull(message = "Los telefonos de contacto de la pagina cliente no pueden estar vacios.")
    private String telefonos;
    @NotNull(message = "La imagen numero 1 de la pagina cliente no puede estar vacio.")
    private String imagen1;
    @NotNull(message = "La imagen numero 2 de la pagina cliente no puede estar vacia.")
    private String imagen2;
    private String disenoCard;
    @NotNull(message = "El id empresa no puede estar vacio.")
    private Empresa idempresa;
}
