package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.response.ReporteAsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.service.ReporteService;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final ReporteService reporteService;

    // GET /api/reportes/asistencia?idEmpresa=1&inicio=2025-01-01&fin=2025-01-31
    @GetMapping("/asistencia")
    public ResponseEntity<List<ReporteAsistenciaResponse>> reporteAsistencia(
            @RequestParam Integer idEmpresa,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(reporteService.reporteAsistenciaEmpleados(idEmpresa, inicio, fin));
    }
}