package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import restosoftteam.restosoft.restosoftv2.dto.request.VentaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPagoPublicaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.EpaycoResponseDto;
import restosoftteam.restosoft.restosoftv2.enums.EstadoMesa;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.ValorInconsistenteException;
import restosoftteam.restosoft.restosoftv2.model.*;
import restosoftteam.restosoft.restosoftv2.repository.*;
import restosoftteam.restosoft.restosoftv2.service.PagoService;
import restosoftteam.restosoft.restosoftv2.service.VentaService;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final ConfiguracionPasarelaRepository pasarelaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final VentaService ventaService;
    private final RestTemplate restTemplate = new RestTemplate();

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final MesaRepository mesaRepository;
    private final PedidoMesaRepository pedidoMesaRepository;

    @Override
    public ConfiguracionPagoPublicaResponse obtenerConfiguracionPublica(Integer idEmpresa) {
        return pasarelaRepository.findByEmpresa_IdAndPasarelaAndActivoTrue(idEmpresa, "EPAYCO")
                .map(config -> ConfiguracionPagoPublicaResponse.builder()
                        .publicKey(config.getPublicKey())
                        .environment("sandbox")
                        .proveedor(config.getPasarela())
                        .activo(config.getActivo())
                        .build())
                .orElseGet(() -> ConfiguracionPagoPublicaResponse.builder()
                        .publicKey("e7b67f3f6f395060d388e29e4c305de7")
                        .environment("sandbox")
                        .proveedor("EPAYCO")
                        .activo(true)
                        .build());
    }

    @Override
    @Transactional
    public void procesarPagoConfirmado(String refPayco, String estado, Double monto, Integer idPedido, Integer idEmpresa, Integer idUsuario) {
        String url = "https://secure.epayco.co/validation/v1/reference/" + refPayco;
        EpaycoResponseDto epaycoResponse = restTemplate.getForObject(url, EpaycoResponseDto.class);

        if (epaycoResponse == null || !epaycoResponse.getSuccess()) {
            throw new ValorInconsistenteException("La transacción no pudo ser verificada con ePayco.");
        }

        EpaycoResponseDto.EpaycoDataDto transData = epaycoResponse.getData();
        if (transData == null) throw new ValorInconsistenteException("Datos de transacción ePayco vacíos.");

        String state = transData.getXTransactionState();
        if (!"Aceptada".equalsIgnoreCase(state)) {
            throw new ValorInconsistenteException("La transacción ePayco no fue aprobada.");
        }

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado tras confirmación ePayco"));

        pedido.setEstado("PAGADO");
        pedido.setTipoPago("ONLINE");
        pedidoRepository.save(pedido);

        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(pedido.getId());
        for (DetallePedido det : detalles) {
            for (UnidadPreparacion uni : det.getUnidades()) {
                uni.setEstado("PAGADO");
            }
        }
        detallePedidoRepository.saveAll(detalles);

        List<PedidoMesa> mesasDelPedido = pedidoMesaRepository.findByPedido_Id(pedido.getId());
        for (PedidoMesa pm : mesasDelPedido) {
            Mesa mesa = pm.getMesa();
            mesa.setEstado(EstadoMesa.LIBRE);
            mesaRepository.save(mesa);
        }

        Integer idMetodoPago = metodoPagoRepository.findAll().stream()
                .filter(m -> m.getNombre().equalsIgnoreCase("TARJETA") || m.getNombre().equalsIgnoreCase("EPAYCO"))
                .findFirst()
                .map(MetodoPago::getId)
                .orElse(1);

        VentaRequest ventaRequest = new VentaRequest();
        ventaRequest.setIdPedido(idPedido);
        ventaRequest.setIdEmpresa(idEmpresa);
        ventaRequest.setIdUsuario(idUsuario);
        ventaRequest.setValorTotal(monto);
        ventaRequest.setObservaciones("Pago electrónico aprobado ePayco Ref: " + refPayco);
        ventaRequest.setIdMetodosPago(Collections.singleton(idMetodoPago));

        ventaService.registrarVenta(ventaRequest);
    }
}