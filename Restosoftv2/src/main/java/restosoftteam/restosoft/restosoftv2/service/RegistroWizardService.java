package restosoftteam.restosoft.restosoftv2.service;

import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.RegistroWizardRequest;

public interface RegistroWizardService {
    void registrarTodo(RegistroWizardRequest request, MultipartFile imagenAdmin);
}