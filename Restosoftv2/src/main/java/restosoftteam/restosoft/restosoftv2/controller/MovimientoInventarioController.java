package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoInventarioResponse>> listar(
            @Parameter(hidden = true) @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(movimientoService.listar(idEmpresa));
    }

    @PostMapping
    public ResponseEntity<MovimientoInventarioResponse> registrarMovimiento(
            @RequestBody MovimientoInventarioRequest request,
            @Parameter(hidden = true) @IdEmpresa Integer idEmpresa) {
        System.out.println("salidas" + request);
        return new ResponseEntity<>(movimientoService.crear(request, idEmpresa), HttpStatus.CREATED);
    }
}