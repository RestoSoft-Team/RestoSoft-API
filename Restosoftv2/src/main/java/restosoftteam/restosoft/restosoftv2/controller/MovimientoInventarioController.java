package restosoftteam.restosoft.restosoftv2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponse>> listar(){
        return ResponseEntity.ok(movimientoService.listar());
    }
}
