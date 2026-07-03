package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.AsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.MarcarAsistenciaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.AsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.MarcarAsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.AsistenciaService;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @PostMapping("/entrada")
    public ResponseEntity<AsistenciaResponse> registrarEntrada(@Valid @RequestBody AsistenciaRequest request) {
        return new ResponseEntity<>(asistenciaService.registrarEntrada(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/salida")
    public ResponseEntity<AsistenciaResponse> registrarSalida(@PathVariable Integer id) {
        return ResponseEntity.ok(asistenciaService.registrarSalida(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AsistenciaResponse>> listarAsistencias(
            @PathVariable Integer idUsuario, @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(asistenciaService.listarAsistenciasPorUsuario(idUsuario, idEmpresa));
    }
    @PostMapping("/marcar")
    public ResponseEntity<MarcarAsistenciaResponse> marcarPorCodigo(
            @Valid @RequestBody MarcarAsistenciaRequest request) {
        return ResponseEntity.ok(asistenciaService.marcarPorCodigoBarras(request));
    }
}