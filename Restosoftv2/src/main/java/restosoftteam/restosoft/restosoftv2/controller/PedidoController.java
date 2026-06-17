package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PedidoController {
    private final PedidoService pedidoService;

    ///  continuar desde pedido controller
}
