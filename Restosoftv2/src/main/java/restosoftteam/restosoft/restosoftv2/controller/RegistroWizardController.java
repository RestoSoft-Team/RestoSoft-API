package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.RegistroWizardRequest;
import restosoftteam.restosoft.restosoftv2.service.RegistroWizardService;

@RestController
@RequestMapping("/api/registro")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RegistroWizardController {

    private final RegistroWizardService registroWizardService;

    @PostMapping(value = "/wizard-completo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> registrarTodo(
            @RequestPart("datos") RegistroWizardRequest request,
            @RequestParam(value = "imagenAdmin", required = false) MultipartFile imagenAdmin) {
        try {
            registroWizardService.registrarTodo(request, imagenAdmin);
            return ResponseEntity.ok("Empresa, Administrador y Plano guardados con éxito");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error en el registro: " + e.getMessage());
        }
    }
}