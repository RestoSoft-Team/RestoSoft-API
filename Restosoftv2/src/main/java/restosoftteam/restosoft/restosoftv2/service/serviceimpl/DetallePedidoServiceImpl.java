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
import restosoftteam.restosoft.restosoftv2.repository.DetallePedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoRepository;
import restosoftteam.restosoft.restosoftv2.service.DetallePedidoService;

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

    @Override
    public DetallePedidoResponse findByDetallePedidoId(Integer id, Integer idEmpresa) {

        boolean existeEmpresa = empresaRepository.existsById(idEmpresa);
        if (!existeEmpresa) {
            throw new RecursoNoEncontradoException("La empresa relacionada al detalle de pedido no existe");
        }

        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El detalle pedido  no se ha encontrado"));

        return detallePedidoMapper.toResponse(detallePedido);

    }

    @Override
    @Transactional
    public DetallePedidoResponse save(DetallePedidoRequest request) {

        Plato plato = platoRepository.findById(request.getIdplato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato relacionado no encontrado."));

        Pedido pedido = pedidoRepository.findById(request.getIdpedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido relacionado no encontrado."));

        DetallePedido detallePedido = detallePedidoMapper.toEntity(request);

        detallePedido.setIdplato(plato);
        detallePedido.setIdpedido(pedido);

        return detallePedidoMapper.toResponse(detallePedidoRepository.save(detallePedido));

    }

    @Override
    public List<DetallePedidoResponse> listAllByIdPedido(int idPedido) {

        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido al cual  se relaciona el detalle pedido."));
        List<DetallePedido> listaDetallePedido = detallePedidoRepository.findAllByPedido_Id(pedido.getId());

        return listaDetallePedido.stream()
                .map(detallePedidoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DetallePedidoResponse update(DetallePedidoRequest request, Integer id) {

        Plato plato = platoRepository.findById(request.getIdplato())
                .orElseThrow(() -> new RecursoNoEncontradoException("Plato relacionado no encontrado."));

        Pedido pedido = pedidoRepository.findById(request.getIdpedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido relacionado no encontrado."));


        DetallePedido detallePedido = detallePedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El detalle pedido  no se ha encontrado"));

        detallePedidoMapper.updateEntityFromDto(request, detallePedido);

        detallePedido.setIdpedido(pedido);
        detallePedido.setIdplato(plato);

        return detallePedidoMapper.toResponse(detallePedidoRepository.save(detallePedido));
    }

    @Override
    @Transactional
    public void deleteByid(Integer id) {
        boolean existeDetallePedido = detallePedidoRepository.existsById(id);
        if (!existeDetallePedido) {
            throw new RecursoNoEncontradoException("El detalle pedido  no se ha encontrado");
        }
        detallePedidoRepository.deleteById(id);
    }
}
