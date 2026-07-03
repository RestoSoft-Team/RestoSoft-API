package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.ProveedorService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(
            @Valid @RequestBody ProveedorRequest request,
            @Parameter(hidden = true) @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(request, idEmpresa));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listar(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(proveedorService.listar(idEmpresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorRequest request,
            @Parameter(hidden = true) @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(proveedorService.actualizar(id, request, idEmpresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        proveedorService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
