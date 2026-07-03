package restosoftteam.restosoft.restosoftv2.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RegistroWizardRequest {
    private EmpresaRequest empresa;
    private EmpleadoRequest admin;
    private List<ZonaRequest> zonas;
    private List<MesaRequest> mesas;
}