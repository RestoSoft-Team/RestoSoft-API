package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

@Getter
@Setter
public class EstiloInterfazEmpresaRequest {
    private String colorPrimario;
    private String colorSecundario;
    private String colorBotones;
    private String colorEnfasisTablas;
    private String colorSidebar;
    @NotNull(message = "El id de empresa no puede estar vacio.")
    private Empresa idempresa;
}
