package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPagoPublicaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.PagoService;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping("/configuracion")
    public ResponseEntity<ConfiguracionPagoPublicaResponse> obtenerConfiguracionPublica(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {
        ConfiguracionPagoPublicaResponse response = pagoService.obtenerConfiguracionPublica(idEmpresa);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirmacion")
    public ResponseEntity<Void> recibirConfirmacion(
            @RequestParam("x_ref_payco") String refPayco,
            @RequestParam("x_transaction_state") String estadoTransaccion,
            @RequestParam("x_amount") Double monto,
            @RequestParam("x_id_invoice") Integer idPedido,
            @RequestParam("x_extra1") Integer idEmpresa,
            @RequestParam("x_extra2") Integer idUsuario
    ) {
        pagoService.procesarPagoConfirmado(refPayco, estadoTransaccion, monto, idPedido, idEmpresa, idUsuario);
        return ResponseEntity.ok().build();
    }
}
