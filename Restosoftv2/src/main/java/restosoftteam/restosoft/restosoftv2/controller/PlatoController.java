package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.service.PlatoService;

import java.util.List;

@RestController
@RequestMapping("/api/plato")
@RequiredArgsConstructor
public class PlatoController {
    private final PlatoService platoService;

    @GetMapping("/{idPlato}")
    public ResponseEntity<PlatoResponse> findById(@PathVariable Integer idPlato) {
        PlatoResponse platoResponse = platoService.findById(idPlato);
        return ResponseEntity.ok(platoResponse);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<PlatoResponse>> findAllByEmpresaId(@PathVariable Integer idEmpresa) {
        List<PlatoResponse> listaplatoResponse = platoService.findAllByEmpresaId(idEmpresa);
        return ResponseEntity.ok(listaplatoResponse);
    }

    @PostMapping
    public ResponseEntity<PlatoResponse> save(@Valid @RequestBody PlatoRequest request){
        PlatoResponse platoResponse = platoService.save(request);
        return new ResponseEntity<>(platoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{idPlato}")
    public ResponseEntity<PlatoResponse> update(@PathVariable Integer idPlato, @Valid @RequestBody PlatoRequest request){
        PlatoResponse platoResponse = platoService.update(request,idPlato);
        return ResponseEntity.ok(platoResponse);
    }

    @DeleteMapping("/{idPlato}")
    public ResponseEntity<Void> delete(@PathVariable Integer idPlato) {
        platoService.delete(idPlato);
        return ResponseEntity.noContent().build();
    }
}
