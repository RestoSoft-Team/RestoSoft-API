package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.DetallePedidoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.DetallePedidoMapper;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Plato;
import restosoftteam.restosoft.restosoftv2.model.UnidadPreparacion;
import restosoftteam.restosoft.restosoftv2.repository.DetallePedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoRepository;
import restosoftteam.restosoft.restosoftv2.repository.UnidadPreparacionRepository;
import restosoftteam.restosoft.restosoftv2.service.DetallePedidoService;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;
    private final EmpresaRepository empresaRepository;
    private final PedidoRepository pedidoRepository;
    private final PlatoRepository platoRepository;

    private final UnidadPreparacionRepository unidadPreparacionRepository;

    @Override
    public DetallePedidoResponse findByDetallePedidoId(Integer id, Integer idEmpresa) {
        boolean existeEmpresa = empresaRepository.existsById(idEmpresa);
        if (!existeEmpresa) {
            throw new RecursoNoEncontradoException("La empresa relacionada al detalle de pedido no existe");
        }

        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El detalle pedido no se ha encontrado"));

        return detallePedidoMapper.toResponse(detallePedido);
    }

    @Override
    @Transactional
    public DetallePedidoResponse save(DetallePedidoRequest request, Integer idEmpresa) {
        Plato plato = platoRepository.findById(request.getIdplato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato relacionado no encontrado."));

        Pedido pedido = pedidoRepository.findById(request.getIdpedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido relacionado no encontrado."));

        if (!pedido.getEmpresa().getId().equals(idEmpresa)) {
            throw new RecursoNoEncontradoException("No tienes permiso para modificar este pedido.");
        }

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPlato(plato);
        detallePedido.setPedido(pedido);
        detallePedido.setCantidad(request.getCantidad());
        detallePedido.setPrecioUnitario(request.getPrecioUnitario());

        for (int i = 0; i < request.getCantidad(); i++) {
            UnidadPreparacion unidad = new UnidadPreparacion();
            unidad.setEstado("PENDIENTE");
            unidad.setDetallePedido(detallePedido);
            detallePedido.getUnidades().add(unidad);
        }

        detallePedidoRepository.save(detallePedido);

        List<UnidadPreparacion> todasLasUnidades = unidadPreparacionRepository.findAllByDetallePedido_Pedido_Id(pedido.getId());

        boolean todasListas = true;
        boolean algunaPreparando = false;

        for (UnidadPreparacion u : todasLasUnidades) {
            if (!"LISTO".equals(u.getEstado())) {
                todasListas = false;
            }
            if ("PREPARANDO".equals(u.getEstado())) {
                algunaPreparando = true;
            }
        }

        if (todasListas) {
            pedido.setEstado("LISTO");
        } else if (algunaPreparando) {
            pedido.setEstado("PREPARANDO");
        } else {
            pedido.setEstado("PENDIENTE");
        }

        pedidoRepository.save(pedido);

        Map<String, String> payload = new HashMap<>();
        payload.put("tipo", "PRODUCTO_AGREGADO");
        payload.put("idPedido", pedido.getId().toString());

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend("/topic/cocina", payload);
            }
        });

        return detallePedidoMapper.toResponse(detallePedido);
    }

    @Override
    public List<DetallePedidoResponse> listAllByPedidoId(int idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido al cual se relaciona el detalle pedido."));

        List<DetallePedido> listaDetallePedido = detallePedidoRepository.findAllByPedido_Id(pedido.getId());

        return listaDetallePedido.stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DetallePedidoResponse update(DetallePedidoRequest request, Integer id) {
        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El detalle pedido no se ha encontrado"));

        if (request.getCantidad() != null) {
            detallePedido.setCantidad(request.getCantidad());
        }
        if (request.getPrecioUnitario() != null) {
            detallePedido.setPrecioUnitario(request.getPrecioUnitario());
        }

        return detallePedidoMapper.toResponse(detallePedidoRepository.save(detallePedido));
    }

    @Transactional
    public void marcarUnidadComoLista(Integer idUnidad) {
        UnidadPreparacion unidad = unidadPreparacionRepository.findById(idUnidad)
                .orElseThrow(() -> new RecursoNoEncontradoException("La unidad de preparación no existe"));

        unidad.setEstado("LISTO");
        unidad.setFechaListo(Instant.now());

        unidadPreparacionRepository.save(unidad);
    }

    @Override
    @Transactional
    public void deleteByid(Integer id) {
        boolean existeDetallePedido = detallePedidoRepository.existsById(id);
        if (!existeDetallePedido) {
            throw new RecursoNoEncontradoException("El detalle pedido no se ha encontrado");
        }
        detallePedidoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void actualizarEstadoUnidad(Integer idUnidad, String estado) {
        UnidadPreparacion unidad = unidadPreparacionRepository.findById(idUnidad)
                .orElseThrow(() -> new RecursoNoEncontradoException("Unidad no encontrada"));

        unidad.setEstado(estado);

        if ("PREPARANDO".equals(estado)) {
            if (unidad.getFechaInicio() == null) {
                unidad.setFechaInicio(Instant.now());
            }
        } else if ("LISTO".equals(estado)) {
            unidad.setFechaListo(Instant.now());
        }

        unidadPreparacionRepository.save(unidad);

        Pedido pedido = unidad.getDetallePedido().getPedido();

        List<UnidadPreparacion> todasLasUnidades = unidadPreparacionRepository.findAllByDetallePedido_Pedido_Id(pedido.getId());

        boolean todasListas = true;
        boolean algunaPreparando = false;

        for (UnidadPreparacion u : todasLasUnidades) {
            if (!"LISTO".equals(u.getEstado())) {
                todasListas = false;
            }
            if ("PREPARANDO".equals(u.getEstado())) {
                algunaPreparando = true;
            }
        }

        if (todasListas) {
            pedido.setEstado("LISTO");
        } else if (algunaPreparando) {
            pedido.setEstado("PREPARANDO");
        } else {
            pedido.setEstado("PENDIENTE");
        }
        pedidoRepository.save(pedido);

        String nombreMesa = pedido.getMesas().stream()
                .map(pm -> pm.getMesa().getNumeroMesa())
                .collect(Collectors.joining(", "));

        String nombrePlato = unidad.getDetallePedido().getPlato().getNombre();

        Map<String, String> payloadCocina = new HashMap<>();
        payloadCocina.put("tipo", "ESTADO_ACTUALIZADO");
        payloadCocina.put("idPedido", pedido.getId().toString());
        payloadCocina.put("estadoUnidad", estado); // 👈 AGREGAMOS EL ESTADO
        payloadCocina.put("mesa", nombreMesa != null ? nombreMesa : "N/A");
        payloadCocina.put("plato", nombrePlato != null ? nombrePlato : "Plato");

        // 👇 ENVÍAR AMBOS WEBSOCKETS DESPUÉS DEL COMMIT DE LA BD
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend("/topic/cocina", payloadCocina);

                if ("LISTO".equals(estado)) {
                    Map<String, String> payloadNotif = new HashMap<>();
                    payloadNotif.put("tipo", "PLATO_LISTO");
                    payloadNotif.put("idPedido", pedido.getId().toString());
                    payloadNotif.put("mesa", nombreMesa != null ? nombreMesa : "N/A");
                    payloadNotif.put("plato", nombrePlato != null ? nombrePlato : "Plato");

                    messagingTemplate.convertAndSend("/topic/notificaciones", payloadNotif);
                }
            }
        });
    }
}