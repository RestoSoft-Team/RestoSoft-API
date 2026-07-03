package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.PlatoService;

import java.util.List;

@RestController
@RequestMapping("/api/plato")
@RequiredArgsConstructor
public class PlatoController {
    private final PlatoService platoService;

    @GetMapping("/{idPlato}")
    public ResponseEntity<PlatoResponse> obtenerPorId(@PathVariable Integer idPlato) {
        return ResponseEntity.ok(platoService.findById(idPlato));
    }

    @GetMapping
    public ResponseEntity<List<PlatoResponse>> listarPorEmpresa(@IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(platoService.findAllByEmpresaId(idEmpresa));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlatoResponse> guardarPlato(
            @Valid @RequestPart("plato") PlatoRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @IdEmpresa Integer idEmpresa){
        return new ResponseEntity<>(platoService.save(request, idEmpresa, imagen), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{idPlato}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PlatoResponse> actualizarPlato(
            @PathVariable Integer idPlato,
            @Valid @RequestPart("plato") PlatoRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen,
            @IdEmpresa Integer idEmpresa){
        return ResponseEntity.ok(platoService.update(request, idPlato, imagen, idEmpresa));
    }

    @DeleteMapping("/{idPlato}")
    public ResponseEntity<Void> eliminarPlato(@PathVariable Integer idPlato) {
        platoService.delete(idPlato);
        return ResponseEntity.noContent().build();
    }
}