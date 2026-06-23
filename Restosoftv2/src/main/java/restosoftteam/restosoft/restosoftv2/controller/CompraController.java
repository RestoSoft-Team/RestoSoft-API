package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restosoftteam.restosoft.restosoftv2.dto.request.CompraRequest;
import restosoftteam.restosoft.restosoftv2.service.CompraService;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @PostMapping
    public ResponseEntity<String> registrar(@Valid @RequestBody CompraRequest request){
        compraService.registrarCompra(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Compra registrada exitosamente");
    }
}
