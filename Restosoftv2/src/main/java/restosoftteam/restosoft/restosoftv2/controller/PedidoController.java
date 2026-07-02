package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdUsuario;
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponse>> obtenerTodosPorEmpresa(@IdEmpresa Integer idEmpresa){
        return ResponseEntity.ok(pedidoService.findAllByEmpresa_Id(idEmpresa));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(
            @Valid @RequestBody PedidoRequest pedidoRequest,
            @IdEmpresa Integer idEmpresa,
            @IdUsuario Integer idUsuario) {

        PedidoResponse pedidoResponse = pedidoService.save(pedidoRequest, idEmpresa, idUsuario);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoResponse> actualizarPedido(@PathVariable Integer idPedido,@Valid @RequestBody PedidoRequest pedidoRequest,@IdEmpresa Integer idEmpresa){
        return ResponseEntity.ok(pedidoService.update(pedidoRequest, idPedido, idEmpresa));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer idPedido){
        pedidoService.delete(idPedido);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<PedidoResponse> obtenerPedidoPorMesa(@PathVariable Integer idMesa, @IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(pedidoService.findPedidoActivoByMesa(idMesa, idEmpresa));
    }

    @PostMapping("/enviar-ticket/{idPedido}")
    public ResponseEntity<Void> enviarTicketCorreo(@PathVariable Integer idPedido, @IdEmpresa Integer idEmpresa) {
        pedidoService.enviarTicketPedido(idPedido, idEmpresa);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/procesar-pago/{idPedido}")
    public ResponseEntity<Void> procesarPago(@PathVariable Integer idPedido, @RequestBody Map<String, Object> payload, @IdEmpresa Integer idEmpresa) {
        String tipoPago = (String) payload.get("tipoPago");
        List<Map<String, Object>> metodosPago = (List<Map<String, Object>>) payload.get("metodosPago");
        pedidoService.procesarPago(idPedido, tipoPago, metodosPago, idEmpresa);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancelar/{idPedido}")
    public ResponseEntity<Void> cancelarPedido(
            @PathVariable Integer idPedido,
            @RequestBody Map<String, String> payload,
            @IdEmpresa Integer idEmpresa) {

        String motivo = payload.get("motivo");
        pedidoService.cancelarPedido(idPedido, motivo, idEmpresa);
        return ResponseEntity.ok().build();
    }
}