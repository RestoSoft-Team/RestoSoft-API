package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

@Getter
@Setter
public class EstiloInterfazEmpresaResponse {

    private Integer id;

    private String primary;
    private String primaryLight;
    private String primarySoft;

    private String background;
    private String surface;
    private String surfaceContainer;

    private String borderColor;

    private String dark;
    private String gray;
    private String soft;

    private String success;
    private String successSoft;

    private String warning;
    private String warningSoft;

    private String danger;
    private String dangerSoft;

}