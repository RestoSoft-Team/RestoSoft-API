package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
@Getter
@Setter
public class EstiloInterfazEmpresaResponse {
    private Integer id;
    private String colorPrimario;
    private String colorSecundario;
    private String colorBotones;
    private String colorEnfasisTablas;
    private String colorSidebar;
    private Empresa idempresa;
}
