package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.service.InsumoCategoriaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/categoria-insumo")
@RequiredArgsConstructor
public class InsumoCategoriaController {

    private final InsumoCategoriaService service;

    @GetMapping
    public ResponseEntity<List<InsumoCategoriaResponse>> listarTodas(){
        List<InsumoCategoriaResponse> categorias = service.listarTodos();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsumoCategoriaResponse> buscarPorId(@PathVariable Integer id){
        InsumoCategoriaResponse categoria = service.buscarPorId(id);
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<InsumoCategoriaResponse> crear(@Valid @RequestBody InsumoCategoriaRequest request){
        InsumoCategoriaResponse nuevaCategoria = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCategoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsumoCategoriaResponse> actualizar(@PathVariable Integer id, @Valid @RequestBody InsumoCategoriaRequest request){
        InsumoCategoriaResponse categoriaActualizada = service.actualizar(id, request);
        return ResponseEntity.ok(categoriaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

