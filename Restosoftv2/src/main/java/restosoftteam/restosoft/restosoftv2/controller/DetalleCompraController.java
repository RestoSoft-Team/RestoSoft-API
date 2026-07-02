package restosoftteam.restosoft.restosoftv2.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restosoftteam.restosoft.restosoftv2.dto.response.DetalleCompraResponse;
import restosoftteam.restosoft.restosoftv2.service.DetalleCompraService;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-compra")
@RequiredArgsConstructor
public class DetalleCompraController {

    private final DetalleCompraService detalleService;


    @GetMapping("/{idCompra}")
    public ResponseEntity<List<DetalleCompraResponse>> listarPorCompra(@PathVariable Integer idCompra){
        return ResponseEntity.ok(detalleService.listarPorCompra(idCompra));
    }
}
