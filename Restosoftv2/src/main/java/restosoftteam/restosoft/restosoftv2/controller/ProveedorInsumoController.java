package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorInsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorInsumoResponse;
import restosoftteam.restosoft.restosoftv2.service.ProveedorInsumoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/proveedor-insumos")
@RequiredArgsConstructor
public class ProveedorInsumoController {

    private final ProveedorInsumoService proveedorInsumoService;

    @PostMapping
    public ResponseEntity<ProveedorInsumoResponse> crear(
            @Valid @RequestBody ProveedorInsumoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(proveedorInsumoService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorInsumoResponse>> listar() {
        return ResponseEntity.ok(proveedorInsumoService.listar());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProveedorInsumoResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorInsumoRequest request) {

        return ResponseEntity.ok(proveedorInsumoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        proveedorInsumoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/proveedor/{idProveedor}")
    public ResponseEntity<List<ProveedorInsumoResponse>> listarPorProveedor(
            @PathVariable Integer idProveedor) {

        return ResponseEntity.ok(
                proveedorInsumoService.listarPorProveedor(idProveedor)
        );
    }

    @GetMapping("/insumo/{idInsumo}")
    public ResponseEntity<List<ProveedorInsumoResponse>> listarPorInsumo(
            @PathVariable Integer idInsumo) {

        return ResponseEntity.ok(
                proveedorInsumoService.listarPorInsumo(idInsumo)
        );
    }
}