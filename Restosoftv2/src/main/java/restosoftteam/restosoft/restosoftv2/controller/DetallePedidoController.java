package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.DetallePedidoService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/detallepedido")
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    @GetMapping("/{idPedido}")
    public ResponseEntity<List<DetallePedidoResponse>> listarPorPedidoId(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(detallePedidoService.listAllByPedidoId(idPedido));
    }

    public ResponseEntity<DetallePedidoResponse> obtenerDetallePedidoPorId(@PathVariable Integer idDetallePedido, @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(detallePedidoService.findByDetallePedidoId(idDetallePedido, idEmpresa));
    }

    @PostMapping
    public ResponseEntity<DetallePedidoResponse> crearDetallePedido(@Valid @RequestBody DetallePedidoRequest detallePedidoRequest, @IdEmpresa Integer idEmpresa) {
        return new ResponseEntity<>(detallePedidoService.save(detallePedidoRequest, idEmpresa), HttpStatus.CREATED);
    }

    @PutMapping("/{idDetallePedido}")
    public ResponseEntity<DetallePedidoResponse> actualizarDetallePedido(@Valid @RequestBody DetallePedidoRequest detallePedidoRequest, @PathVariable Integer idDetallePedido) {
        return ResponseEntity.ok(detallePedidoService.update(detallePedidoRequest, idDetallePedido));
    }

    @DeleteMapping("/{idDetallePedido}")
    public ResponseEntity<Void> eliminarDetallePedido(@PathVariable Integer idDetallePedido) {
        detallePedidoService.deleteByid(idDetallePedido);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/unidad/{idUnidad}/estado/{estado}")
    public ResponseEntity<Void> actualizarEstadoUnidad(@PathVariable Integer idUnidad, @PathVariable String estado) {
        detallePedidoService.actualizarEstadoUnidad(idUnidad, estado);
        return ResponseEntity.ok().build();
    }
}