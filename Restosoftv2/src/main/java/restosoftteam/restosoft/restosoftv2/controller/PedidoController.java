package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

import java.util.List;

@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponse> obtenerPorId(@PathVariable Integer id){
        return ResponseEntity.ok(pedidoService.findById(id));
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<PedidoResponse>> obtenerTodosPorIdEmpresa(@PathVariable Integer idEmpresa){
        return ResponseEntity.ok(pedidoService.findAllByEmpresa_Id(idEmpresa));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crearPedido(@Valid @RequestBody PedidoRequest pedidoRequest){
        PedidoResponse pedidoResponse = pedidoService.save(pedidoRequest);
        return new ResponseEntity<>(pedidoResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<PedidoResponse> actualizarPedido(@PathVariable Integer idPedido, @Valid @RequestBody PedidoRequest pedidoRequest){
        return ResponseEntity.ok(pedidoService.update(pedidoRequest, idPedido));
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer idPedido){
        pedidoService.delete(idPedido);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<PedidoResponse> obtenerPedidoPorMesa(@PathVariable Integer idMesa) {
        return ResponseEntity.ok(pedidoService.findPedidoActivoByMesa(idMesa));
    }
}