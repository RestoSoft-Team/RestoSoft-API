package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.service.GeminiService;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/zonas")
@RequiredArgsConstructor
public class PlanoController {

    private final GeminiService geminiService;

    @PostMapping("/upload-plano")
    public ResponseEntity<?> subirYAnalizarPlano(@RequestParam("imagen") MultipartFile imagen) {
        try {
            String urlPlanoGuardado = "http://localhost:8080/uploads/plano_temp.jpg";

            String jsonMesas = geminiService.analizarImagenPlano(imagen);

            Map<String, Object> response = new HashMap<>();
            response.put("url", urlPlanoGuardado);
            response.put("mesasDetectadas", jsonMesas);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar el plano: " + e.getMessage());
        }
    }
}