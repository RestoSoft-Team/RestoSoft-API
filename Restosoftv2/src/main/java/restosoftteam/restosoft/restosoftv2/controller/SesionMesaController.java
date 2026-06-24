package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MesaMapper;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/sesion")
@RequiredArgsConstructor
public class SesionMesaController {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    private final Map<Integer, Integer> sesionesActivas = new ConcurrentHashMap<>();

    @PostMapping("/mesa-activa/{idMesa}")
    public ResponseEntity<Void> asignarMesaActiva(
            @PathVariable Integer idMesa,
            @RequestHeader("X-User-Id") Integer idUsuario) {

        Mesa mesa = mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("La mesa no existe"));

        for (Map.Entry<Integer, Integer> entry : sesionesActivas.entrySet()) {
            if (entry.getValue().equals(idMesa) && !entry.getKey().equals(idUsuario)) {
                return ResponseEntity.status(HttpStatus.LOCKED).build();
            }
        }

        sesionesActivas.put(idUsuario, idMesa);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mesa-activa")
    public ResponseEntity<MesaResponse> obtenerMesaActiva(
            @RequestHeader("X-User-Id") Integer idUsuario) {

        Integer idMesa = sesionesActivas.get(idUsuario);

        if (idMesa == null) {
            return ResponseEntity.noContent().build();
        }

        return mesaRepository.findById(idMesa)
                .map(mesaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/mesa-activa")
    public ResponseEntity<Void> liberarMesaActiva(
            @RequestHeader("X-User-Id") Integer idUsuario) {

        sesionesActivas.remove(idUsuario);
        return ResponseEntity.noContent().build();
    }
}