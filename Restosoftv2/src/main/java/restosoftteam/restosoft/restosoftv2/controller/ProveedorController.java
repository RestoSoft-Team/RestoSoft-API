package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.service.ProveedorService;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
@RequiredArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;

    @PostMapping
    public ResponseEntity<ProveedorResponse> crear(@Valid @RequestBody ProveedorRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.crear(request));
    }

    @GetMapping
    public ResponseEntity<List<ProveedorResponse>> listar(){
        return ResponseEntity.ok(proveedorService.listar());
    }
}
