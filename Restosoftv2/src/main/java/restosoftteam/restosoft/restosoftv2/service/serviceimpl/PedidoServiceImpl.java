package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.DetallePedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.enums.EstadoMesa;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.DetallePedidoMapper;
import restosoftteam.restosoft.restosoftv2.mapper.PedidoMapper;
import restosoftteam.restosoft.restosoftv2.model.*;
import restosoftteam.restosoft.restosoftv2.repository.*;
import restosoftteam.restosoft.restosoftv2.service.EmailService;
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoServiceImpl implements PedidoService {

    private final org.springframework.messaging.simp.SimpMessagingTemplate messagingTemplate;

    private final PedidoMapper pedidoMapper;
    private final EmpresaRepository empresaRepository;
    private final PedidoRepository pedidoRepository;
    private final MesaRepository mesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoMesaRepository pedidoMesaRepository;
    private final PedidoUsuarioRepository pedidoUsuarioRepository;
    private final PlatoRepository platoRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final DetallePedidoMapper detallePedidoMapper;
    private final EmailService emailService;

    @Override
    public PedidoResponse findById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido no se ha encontrado"));
        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public List<PedidoResponse> findAllByEmpresa_Id(Integer idEmpresa) {
        if (!empresaRepository.existsById(idEmpresa)) {
            throw new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado.");
        }

        List<Pedido> pedidos = pedidoRepository.findAllByEmpresa_Id(idEmpresa);
        List<PedidoResponse> responses = new ArrayList<>();

        for (Pedido p : pedidos) {
            PedidoResponse res = new PedidoResponse();
            res.setId(p.getId());
            res.setObservaciones(p.getObservaciones());
            res.setEstado(p.getEstado());
            res.setFecha(p.getFecha());
            res.setEmailCliente(p.getEmailCliente());

            List<String> nombresMeseros = new ArrayList<>();
            for (PedidoUsuario pu : p.getUsuarios()) {
                nombresMeseros.add(pu.getUsuario().getNombres() + " " + pu.getUsuario().getApellidos());
            }
            res.setNombresMeseros(nombresMeseros);

            List<PedidoMesa> mesasDelPedido = pedidoMesaRepository.findByPedido_Id(p.getId());
            List<String> numerosMesas = new ArrayList<>();
            for (PedidoMesa pm : mesasDelPedido) {
                numerosMesas.add("Mesa " + pm.getMesa().getNumeroMesa());
            }
            res.setNumerosMesas(numerosMesas);

            List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(p.getId());
            res.setDetalles(detalles.stream().map(detallePedidoMapper::toResponse).toList());

            responses.add(res);
        }

        return responses;
    }

    @Override
    @Transactional
    public PedidoResponse save(PedidoRequest request, Integer idEmpresa, Integer idUsuario) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado."));

        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setEmpresa(empresa);
        pedido = pedidoRepository.save(pedido);

        // 1. Guardar Mesas
        List<PedidoMesa> mesasAsignadas = new ArrayList<>();
        for (int i = 0; i < request.getIdMesas().size(); i++) {
            Integer idMesa = request.getIdMesas().get(i);
            Mesa mesa = mesaRepository.findById(idMesa)
                    .orElseThrow(() -> new RecursoNoEncontradoException("La mesa con ID " + idMesa + " no se ha encontrado."));

            if (mesa.getEstado() == EstadoMesa.OCUPADA) {
                throw new RegistroYaExistenteException("La Mesa " + mesa.getNumeroMesa() + " ya está ocupada.");
            }

            mesa.setEstado(EstadoMesa.OCUPADA);
            mesaRepository.save(mesa);

            PedidoMesa pm = new PedidoMesa();
            pm.setPedido(pedido);
            pm.setMesa(mesa);
            pm.setEsPrincipal(i == 0);
            mesasAsignadas.add(pm);
        }
        pedidoMesaRepository.saveAll(mesasAsignadas);

        if (!request.getIdUsuarios().contains(idUsuario)) {
            request.getIdUsuarios().add(idUsuario);
        }

        List<PedidoUsuario> meserosAsignados = new ArrayList<>();
        for (Integer idUsu : request.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsu)
                    .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con ID " + idUsu + " no se ha encontrado."));
            PedidoUsuario pu = new PedidoUsuario();
            pu.setPedido(pedido);
            pu.setUsuario(usuario);
            meserosAsignados.add(pu);
        }
        pedidoUsuarioRepository.saveAll(meserosAsignados);

        List<Map<String, Object>> itemsParaEmail = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        if (request.getDetalles() != null && !request.getDetalles().isEmpty()) {
            List<DetallePedido> detalles = new ArrayList<>();
            for (DetallePedidoRequest dr : request.getDetalles()) {
                Plato plato = platoRepository.findById(dr.getIdplato())
                        .orElseThrow(() -> new RecursoNoEncontradoException("Plato no encontrado."));

                DetallePedido dp = new DetallePedido();
                dp.setPedido(pedido);
                dp.setPlato(plato);
                dp.setCantidad(dr.getCantidad());
                dp.setPrecioUnitario(dr.getPrecioUnitario());

                BigDecimal subtotal = dr.getPrecioUnitario().multiply(BigDecimal.valueOf(dr.getCantidad()));
                Map<String, Object> item = new HashMap<>();
                item.put("cantidad", dr.getCantidad());
                item.put("nombre", plato.getNombre());
                item.put("precio", dr.getPrecioUnitario());
                item.put("subtotal", subtotal);
                itemsParaEmail.add(item);
                total = total.add(subtotal);

                List<UnidadPreparacion> unidades = new ArrayList<>();
                for (int i = 0; i < dr.getCantidad(); i++) {
                    UnidadPreparacion unidad = new UnidadPreparacion();
                    unidad.setDetallePedido(dp);
                    unidad.setEstado("PENDIENTE");
                    unidades.add(unidad);
                }
                dp.setUnidades(unidades);

                detalles.add(dp);
            }
            detallePedidoRepository.saveAll(detalles);
        }

        final String emailCliente = request.getEmailCliente();
        final String emailEmpresa = empresa.getEmail();
        final Integer pedidoIdFinal = pedido.getId();
        final String meseroFinal = meserosAsignados.stream().map(pu -> pu.getUsuario().getNombres()).collect(Collectors.joining(", "));
        final String mesaFinal = mesasAsignadas.stream().map(m -> m.getMesa().getNumeroMesa()).collect(Collectors.joining(", "));
        final List<Map<String, Object>> itemsFinal = itemsParaEmail;
        final BigDecimal totalFinal = total;

        Map<String, String> payload = new HashMap<>();
        payload.put("tipo", "NUEVO_PEDIDO");
        payload.put("idPedido", pedido.getId().toString());

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend("/topic/cocina", payload);

                emailService.enviarTicketPedido(
                        emailCliente,
                        emailEmpresa,
                        pedidoIdFinal,
                        mesaFinal,
                        meseroFinal,
                        itemsFinal,
                        totalFinal
                );
            }
        });

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse update(PedidoRequest request, Integer id, Integer idEmpresa) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido que se intentar actualizar no se ha encontrado."));

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado."));

        pedidoMapper.updateEntityFromDto(request, pedido);
        pedido.setEmpresa(empresa);

        return pedidoMapper.toResponse(pedidoRepository.save(pedido));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido que se intenta eliminar no se ha encontrado."));
        pedidoRepository.delete(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse findPedidoActivoByMesa(Integer idMesa, Integer idEmpresa) {
        List<PedidoMesa> pedidosMesa = pedidoMesaRepository.findByMesa_Id(idMesa);

        if (pedidosMesa.isEmpty()) {
            throw new RecursoNoEncontradoException("La mesa no tiene ningún pedido asignado.");
        }

        Pedido pedido = null;
        for (PedidoMesa pm : pedidosMesa) {
            Pedido p = pm.getPedido();
            if (!"PAGADO".equals(p.getEstado()) && !"CANCELADO".equals(p.getEstado())) {
                pedido = p;
                break;
            }
        }

        if (pedido == null) {
            throw new RecursoNoEncontradoException("La mesa no tiene ningún pedido activo.");
        }

        if (!pedido.getEmpresa().getId().equals(idEmpresa)) {
            throw new RecursoNoEncontradoException("La mesa no tiene ningún pedido activo.");
        }

        PedidoResponse response = new PedidoResponse();
        response.setId(pedido.getId());
        response.setObservaciones(pedido.getObservaciones());
        response.setEstado(pedido.getEstado());
        response.setFecha(pedido.getFecha());

        List<String> nombresMeseros = new ArrayList<>();
        for (PedidoUsuario pu : pedido.getUsuarios()) {
            nombresMeseros.add(pu.getUsuario().getNombres() + " " + pu.getUsuario().getApellidos());
        }
        response.setNombresMeseros(nombresMeseros);

        List<PedidoMesa> todasLasMesasPedido = pedidoMesaRepository.findByPedido_Id(pedido.getId());
        List<String> numerosMesas = new ArrayList<>();
        for (PedidoMesa pm : todasLasMesasPedido) {
            numerosMesas.add("Mesa " + pm.getMesa().getNumeroMesa());
        }
        response.setNumerosMesas(numerosMesas);

        return response;
    }

    @Override
    @Transactional
    public void enviarTicketPedido(Integer idPedido, Integer idEmpresa) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        if (pedido.getEmailCliente() == null || pedido.getEmailCliente().trim().isEmpty()) {
            throw new RecursoNoEncontradoException("El pedido no tiene un correo de cliente registrado.");
        }

        String emailCliente = pedido.getEmailCliente();
        String emailEmpresa = pedido.getEmpresa().getEmail();
        String mesa = pedido.getMesas().stream().map(m -> m.getMesa().getNumeroMesa()).collect(Collectors.joining(", "));
        String mesero = pedido.getUsuarios().stream().map(u -> u.getUsuario().getNombres()).collect(Collectors.joining(", "));

        List<Map<String, Object>> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(pedido.getId());
        for (DetallePedido det : detalles) {
            int cantidad = det.getUnidades() != null ? det.getUnidades().size() : det.getCantidad();
            BigDecimal subtotal = det.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));

            Map<String, Object> item = new HashMap<>();
            item.put("cantidad", cantidad);
            item.put("nombre", det.getPlato().getNombre());
            item.put("precio", det.getPrecioUnitario());
            item.put("subtotal", subtotal);
            items.add(item);

            total = total.add(subtotal);
        }

        emailService.enviarTicketPedido(emailCliente, emailEmpresa, pedido.getId(), mesa, mesero, items, total);
    }

    @Override
    @Transactional
    public void pagarPedidoDividido(Integer idPedido, List<Map<String, Object>> divisiones, Integer idEmpresa) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        if (pedido.getEmailCliente() == null || pedido.getEmailCliente().trim().isEmpty()) {
            throw new RecursoNoEncontradoException("El pedido no tiene un correo de cliente registrado.");
        }

        pedido.setEstado("PAGADO");
        pedido.setTipoPago("DIVIDIDO");
        pedidoRepository.save(pedido);

        String emailCliente = pedido.getEmailCliente();
        String emailEmpresa = pedido.getEmpresa().getEmail();
        String mesa = pedido.getMesas().stream().map(m -> m.getMesa().getNumeroMesa()).collect(Collectors.joining(", "));
        String mesero = pedido.getUsuarios().stream().map(u -> u.getUsuario().getNombres()).collect(Collectors.joining(", "));

        List<Map<String, Object>> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(pedido.getId());
        for (DetallePedido det : detalles) {
            int cantidad = det.getUnidades() != null ? det.getUnidades().size() : det.getCantidad();
            BigDecimal sub = det.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));
            Map<String, Object> item = new HashMap<>();
            item.put("cantidad", cantidad);
            item.put("nombre", det.getPlato().getNombre());
            item.put("subtotal", sub);
            items.add(item);
            subtotal = subtotal.add(sub);
        }

        emailService.enviarFacturaPago(emailCliente, emailEmpresa, pedido.getId(), mesa, mesero, items, subtotal, divisiones, "DIVIDIDO");
    }

    @Override
    @Transactional
    public void procesarPago(Integer idPedido, String tipoPago, List<Map<String, Object>> metodosPago, Integer idEmpresa) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        pedido.setEstado("PAGADO");
        pedido.setTipoPago(tipoPago);
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

        if (pedido.getEmailCliente() != null && !pedido.getEmailCliente().trim().isEmpty()) {
            String emailCliente = pedido.getEmailCliente();
            String emailEmpresa = pedido.getEmpresa().getEmail();
            String mesaStr = pedido.getMesas().stream().map(m -> m.getMesa().getNumeroMesa()).collect(Collectors.joining(", "));
            String meseroStr = pedido.getUsuarios().stream().map(u -> u.getUsuario().getNombres()).collect(Collectors.joining(", "));

            List<Map<String, Object>> items = new ArrayList<>();
            BigDecimal subtotal = BigDecimal.ZERO;
            for (DetallePedido det : detalles) {
                int cantidad = det.getUnidades() != null ? det.getUnidades().size() : det.getCantidad();
                BigDecimal sub = det.getPrecioUnitario().multiply(BigDecimal.valueOf(cantidad));
                Map<String, Object> item = new HashMap<>();
                item.put("cantidad", cantidad);
                item.put("nombre", det.getPlato().getNombre());
                item.put("subtotal", sub);
                items.add(item);
                subtotal = subtotal.add(sub);
            }

            emailService.enviarFacturaPago(emailCliente, emailEmpresa, pedido.getId(), mesaStr, meseroStr, items, subtotal, metodosPago, tipoPago);
        }
    }

    @Override
    @Transactional
    public void cancelarPedido(Integer idPedido, String motivo, Integer idEmpresa) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));

        if (!pedido.getEmpresa().getId().equals(idEmpresa)) {
            throw new RecursoNoEncontradoException("El pedido no pertenece a la empresa");
        }

        pedido.setEstado("CANCELADO");
        String obsPrevias = pedido.getObservaciones() != null ? pedido.getObservaciones() + " | " : "";
        pedido.setObservaciones(obsPrevias + "CANCELADO POR: " + motivo);
        pedidoRepository.save(pedido);

        List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(pedido.getId());
        for (DetallePedido det : detalles) {
            if (det.getUnidades() != null) {
                for (UnidadPreparacion uni : det.getUnidades()) {
                    if (!"ENTREGADO".equals(uni.getEstado()) && !"PAGADO".equals(uni.getEstado())) {
                        uni.setEstado("CANCELADO");
                    }
                }
            }
        }
        detallePedidoRepository.saveAll(detalles);

        List<PedidoMesa> mesasDelPedido = pedidoMesaRepository.findByPedido_Id(pedido.getId());
        for (PedidoMesa pm : mesasDelPedido) {
            Mesa mesa = pm.getMesa();
            mesa.setEstado(EstadoMesa.LIBRE);
            mesaRepository.save(mesa);
        }

        Map<String, String> payloadCocina = new HashMap<>();
        payloadCocina.put("tipo", "PEDIDO_CANCELADO");
        payloadCocina.put("idPedido", pedido.getId().toString());

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                messagingTemplate.convertAndSend("/topic/cocina", payloadCocina);
            }
        });
    }
}