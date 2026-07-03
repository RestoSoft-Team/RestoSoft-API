package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.VentaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.VentaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.VentaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/ventas")
@RequiredArgsConstructor
public class VentaController {
    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> registrar(@RequestBody VentaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrarVenta(request));
    }

    @GetMapping
    public ResponseEntity<List<VentaResponse>> listarPorEmpresa(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(ventaService.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/fecha")
    public ResponseEntity<List<VentaResponse>> listarPorFecha(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ventaService.listarPorFecha(fecha, idEmpresa));
    }

    @GetMapping("/total-dia")
    public ResponseEntity<Double> obtenerTotalDia(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ventaService.obtenerTotalDia(fecha, idEmpresa));
    }
}