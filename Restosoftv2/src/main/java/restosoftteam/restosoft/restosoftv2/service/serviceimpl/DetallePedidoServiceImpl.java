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
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DetallePedidoServiceImpl implements DetallePedidoService {

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
    public DetallePedidoResponse save(DetallePedidoRequest request) {
        Plato plato = platoRepository.findById(request.getIdplato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato relacionado no encontrado."));

        Pedido pedido = pedidoRepository.findById(request.getIdpedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido relacionado no encontrado."));

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

        return detallePedidoMapper.toResponse(detallePedidoRepository.save(detallePedido));
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
    }
}