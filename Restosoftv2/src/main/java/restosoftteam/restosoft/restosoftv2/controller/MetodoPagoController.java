package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restosoftteam.restosoft.restosoftv2.dto.response.MetodoPagoResponse;
import restosoftteam.restosoft.restosoftv2.service.MetodoPagoService;

import java.util.List;

@RestController
@RequestMapping("/api/metodos-pago")
@RequiredArgsConstructor
public class MetodoPagoController {

    private final MetodoPagoService metodoPagoService;

    @GetMapping
    public ResponseEntity<List<MetodoPagoResponse>> listar(){
        return ResponseEntity.ok(metodoPagoService.listar());
    }
}
