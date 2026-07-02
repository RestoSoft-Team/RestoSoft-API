package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MesaMapper;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdUsuario;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/sesion")
@RequiredArgsConstructor
public class SesionMesaController {

    private final MesaRepository mesaRepository;
    private final MesaMapper mesaMapper;

    private final Map<Integer, Integer> mesasAbiertasPorUsuario = new ConcurrentHashMap<>();

    private final Map<Integer, Integer> pedidosAbiertosPorUsuario = new ConcurrentHashMap<>();

    @PostMapping("/mesa-activa/{idMesa}")
    public ResponseEntity<Void> abrirMesaEnSesion(
            @PathVariable Integer idMesa,
            @IdUsuario Integer idUsuario) {

        mesaRepository.findById(idMesa)
                .orElseThrow(() -> new RuntimeException("La mesa no existe"));

        mesasAbiertasPorUsuario.put(idUsuario, idMesa);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/mesa-activa")
    public ResponseEntity<MesaResponse> obtenerMesaAbierta(
            @IdUsuario Integer idUsuario) {

        Integer idMesa = mesasAbiertasPorUsuario.get(idUsuario);

        if (idMesa == null) {
            return ResponseEntity.noContent().build();
        }

        return mesaRepository.findById(idMesa)
                .map(mesaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/mesa-activa")
    public ResponseEntity<Void> cerrarMesaEnSesion(
            @IdUsuario Integer idUsuario) {

        mesasAbiertasPorUsuario.remove(idUsuario);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pedido-activo/{idPedido}")
    public ResponseEntity<Void> abrirPedidoEnSesion(@PathVariable Integer idPedido, @IdUsuario Integer idUsuario) {
        pedidosAbiertosPorUsuario.put(idUsuario, idPedido);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pedido-activo")
    public ResponseEntity<Integer> obtenerPedidoAbierto(@IdUsuario Integer idUsuario) {
        Integer idPedido = pedidosAbiertosPorUsuario.get(idUsuario);
        if (idPedido == null) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(idPedido);
    }

    @DeleteMapping("/pedido-activo")
    public ResponseEntity<Void> cerrarPedidoEnSesion(@IdUsuario Integer idUsuario) {
        pedidosAbiertosPorUsuario.remove(idUsuario);
        return ResponseEntity.noContent().build();
    }
}