package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.InsumoService;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*") // allowedHeaders="*" es vital para que acepte el header Authorization
@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    public ResponseEntity<InsumoResponse> crearInsumo(
            @IdEmpresa Integer idEmpresa,
            @Valid @RequestBody InsumoRequest insumoRequest) {
        return new ResponseEntity<>(insumoService.crear(idEmpresa, insumoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponse> actualizarInsumo(
            @PathVariable Integer id,
            @IdEmpresa Integer idEmpresa,
            @Valid @RequestBody InsumoRequest insumoRequest) {
        return ResponseEntity.ok(insumoService.actualizar(id, idEmpresa, insumoRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInsumo(
            @PathVariable Integer id,
            @IdEmpresa Integer idEmpresa) {
        insumoService.eliminar(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<InsumoResponse>> listarInsumosPorEmpresa(
            @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(insumoService.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoResponse> obtenerInsumoPorId(
            @PathVariable Integer id,
            @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(insumoService.obtenerPorId(id, idEmpresa));
    }
}