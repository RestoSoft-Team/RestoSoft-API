package restosoftteam.restosoft.restosoftv2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponse>> listar(){
        return ResponseEntity.ok(movimientoService.listar());
    }

    @PostMapping
    public ResponseEntity<MovimientoInventarioResponse> registrarMovimiento(@RequestBody MovimientoInventarioRequest request) {
        return new ResponseEntity<>(movimientoService.crear(request), HttpStatus.CREATED);
    }
}
