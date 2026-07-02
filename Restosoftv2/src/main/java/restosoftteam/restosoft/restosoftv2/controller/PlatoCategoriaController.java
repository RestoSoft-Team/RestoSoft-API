package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.PlatoCategoriaService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class PlatoCategoriaController {
    private final PlatoCategoriaService platoCategoriaService;

    @PostMapping
    public ResponseEntity<PlatoCategoriaResponse> crear(@Valid @RequestBody PlatoCategoriaRequest request, @IdEmpresa Integer idEmpresa) {
        return new ResponseEntity<>(platoCategoriaService.save(request, idEmpresa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatoCategoriaResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody PlatoCategoriaRequest request) {
        return ResponseEntity.ok(platoCategoriaService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        platoCategoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlatoCategoriaResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(platoCategoriaService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlatoCategoriaResponse>> listarPorEmpresa(@IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(platoCategoriaService.findAllByEmpresa(idEmpresa));
    }
}