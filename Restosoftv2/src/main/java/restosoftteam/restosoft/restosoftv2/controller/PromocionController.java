package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PromocionRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PromocionResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.PromocionService;
import java.util.List;

@RestController
@RequestMapping("/api/promociones")
@RequiredArgsConstructor
public class PromocionController {
    private final PromocionService service;

    @PostMapping
    public ResponseEntity<PromocionResponse> crear(
            @Valid
            @RequestBody PromocionRequest request) {
        return new ResponseEntity<>(service.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponse> actualizar(
            @PathVariable Integer id,
            @Valid
            @RequestBody PromocionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromocionResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PromocionResponse>> listarPorEmpresa(
            @Parameter(hidden = true) @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(service.findAllByEmpresa(idEmpresa));
    }

    @GetMapping("/vigentes")
    public ResponseEntity<List<PromocionResponse>> listarVigentes() {
        return ResponseEntity.ok(service.findVigentes());
    }
}