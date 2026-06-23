package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.service.InsumoService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/insumos")
@RequiredArgsConstructor
public class InsumoController {

    private final InsumoService insumoService;

    @PostMapping
    public ResponseEntity<InsumoResponse> crearInsumo(@Valid @RequestBody InsumoRequest insumoRequest) {
        return new ResponseEntity<>(insumoService.crear(insumoRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoResponse> actualizarInsumo(@PathVariable Integer id, @Valid @RequestBody InsumoRequest insumoRequest) {
        return ResponseEntity.ok(insumoService.actualizar(id, insumoRequest));
    }

    @DeleteMapping("/{id}/empresa/{idEmpresa}")
    public ResponseEntity<Void> eliminarInsumo(@PathVariable Integer id, @PathVariable Integer idEmpresa) {
        insumoService.eliminar(id, idEmpresa);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<InsumoResponse>> listarInsumosPorEmpresa(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(insumoService.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/{id}/empresa/{idEmpresa}")
    public ResponseEntity<InsumoResponse> obtenerInsumoPorId(@PathVariable Integer id, @PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(insumoService.obtenerPorId(id, idEmpresa));
    }
}