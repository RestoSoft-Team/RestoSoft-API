package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
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

    // Memoria temporal: Key -> IdUsuario, Value -> IdMesa
    // No bloquea la mesa, solo recuerda qué mesa abrió cada mesero
    private final Map<Integer, Integer> mesasAbiertasPorUsuario = new ConcurrentHashMap<>();

    @PostMapping("/mesa-activa/{idMesa}")
    public ResponseEntity<Void> abrirMesaEnSesion(
            @PathVariable Integer idMesa,
            @RequestHeader("X-User-Id") Integer idUsuario) {

        // Solo valida que la mesa exista, NO la bloquea
        mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("La mesa no existe"));

        mesasAbiertasPorUsuario.put(idUsuario, idMesa);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mesa-activa")
    public ResponseEntity<MesaResponse> obtenerMesaAbierta(
            @RequestHeader("X-User-Id") Integer idUsuario) {

        Integer idMesa = mesasAbiertasPorUsuario.get(idUsuario);

        if (idMesa == null) {
            return ResponseEntity.noContent().build(); // 204: No tiene mesa asignada
        }

        return mesaRepository.findById(idMesa)
                .map(mesaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/mesa-activa")
    public ResponseEntity<Void> cerrarMesaEnSesion(
            @RequestHeader("X-User-Id") Integer idUsuario) {

        mesasAbiertasPorUsuario.remove(idUsuario);
        return ResponseEntity.noContent().build();
    }
}