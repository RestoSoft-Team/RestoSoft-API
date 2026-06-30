package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdUsuario; // 👈 IMPORT
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
}