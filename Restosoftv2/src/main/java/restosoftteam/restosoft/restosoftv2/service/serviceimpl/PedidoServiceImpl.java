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
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoServiceImpl implements PedidoService {

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
    public PedidoResponse save(PedidoRequest request, Integer idEmpresa) {
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

        // 2. Guardar Meseros
        List<PedidoUsuario> meserosAsignados = new ArrayList<>();
        for (Integer idUsuario : request.getIdUsuarios()) {
            Usuario usuario = usuarioRepository.findById(idUsuario)
                    .orElseThrow(() -> new RecursoNoEncontradoException("El usuario con ID " + idUsuario + " no se ha encontrado."));
            PedidoUsuario pu = new PedidoUsuario();
            pu.setPedido(pedido);
            pu.setUsuario(usuario);
            meserosAsignados.add(pu);
        }
        pedidoUsuarioRepository.saveAll(meserosAsignados);

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

                List<UnidadPreparacion> unidades = new ArrayList<>();
                for (int i = 0; i < dr.getCantidad(); i++) {
                    UnidadPreparacion unidad = new UnidadPreparacion();
                    unidad.setDetallePedido(dp);
                    unidad.setEstado("PENDIENTE");
                    unidad.setFechaInicio(Instant.now());
                    unidades.add(unidad);
                }
                dp.setUnidades(unidades);

                detalles.add(dp);
            }
            detallePedidoRepository.saveAll(detalles);
        }

        return pedidoMapper.toResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse update(PedidoRequest request, Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido que se intentar actualizar no se ha encontrado."));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
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
        Pedido pedido = pedidosMesa.get(0).getPedido();

        if (!pedido.getEmpresa().getId().equals(idEmpresa)) {
            throw new RecursoNoEncontradoException("La mesa no tiene ningún pedido asignado.");
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
}