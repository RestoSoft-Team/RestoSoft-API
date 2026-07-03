package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.CierreCajaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.CierreCajaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.CierreCajaService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cierre-caja")
@RequiredArgsConstructor
public class CierreCajaController {
    private final CierreCajaService service;

    @PostMapping
    public ResponseEntity<CierreCajaResponse> registrarOActualizar(@RequestBody CierreCajaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarOActualizarCierre(request));
    }

    @GetMapping
    public ResponseEntity<List<CierreCajaResponse>> listarPorEmpresa(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(service.listarPorEmpresa(idEmpresa));
    }

    @GetMapping("/fecha")
    public ResponseEntity<CierreCajaResponse> obtenerCierrePorFecha(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.obtenerCierrePorFecha(idEmpresa, fecha));
    }

    @GetMapping("/resumen")
    public ResponseEntity<CierreCajaResponse> obtenerResumenDiario(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(service.obtenerResumenDiario(idEmpresa, fecha));
    }
}
