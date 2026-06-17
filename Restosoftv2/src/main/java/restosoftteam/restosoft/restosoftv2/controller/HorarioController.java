package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.HorarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.HorarioResponse;
import restosoftteam.restosoft.restosoftv2.service.HorarioService;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    @PostMapping
    public ResponseEntity<HorarioResponse> crearHorario(@Valid @RequestBody HorarioRequest request) {
        return new ResponseEntity<>(horarioService.crearHorario(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioResponse> actualizarHorario(
            @PathVariable Integer id, @Valid @RequestBody HorarioRequest request) {
        return ResponseEntity.ok(horarioService.actualizarHorario(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Integer id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/usuario/{idUsuario}/empresa/{idEmpresa}")
    public ResponseEntity<List<HorarioResponse>> listarHorarios(
            @PathVariable Integer idUsuario, @PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(horarioService.listarHorariosPorUsuario(idUsuario, idEmpresa));
    }
}