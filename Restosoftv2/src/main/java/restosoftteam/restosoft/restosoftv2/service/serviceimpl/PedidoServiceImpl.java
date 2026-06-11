package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PedidoMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Mesa;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.MesaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.PedidoService;

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

    @Override
    public PedidoResponse findById(Integer id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido no se ha encontrado"));

        return pedidoMapper.toResponse(pedido);

    }

    @Override
    public List<PedidoResponse> findAllByEmpresa_Id(Integer idEmpresa) {

        if(!empresaRepository.existsById(idEmpresa)){
            throw new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado.");
        }

        return pedidoRepository.findAllByEmpresa_Id(idEmpresa)
                .stream().map(pedidoMapper::toResponse).toList();

    }

    @Override
    @Transactional
    public PedidoResponse update(PedidoRequest request, Integer id) {

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido que se intentar actualizar no se ha encontrado."));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado."));

        Mesa mesa = mesaRepository.findById(request.getIdmesa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada al pedido no se ha encontrado."));

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario relacionado al pedido no se ha encontrado"));


        pedidoMapper.updateEntityFromDto(request, pedido);
        pedido.setEmpresa(empresa);
        pedido.setMesa(mesa);
        pedido.setUsuario(usuario);


        return pedidoMapper.toResponse(pedidoRepository.save(pedido));

    }

    @Override
    @Transactional
    public PedidoResponse save(PedidoRequest request) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada al pedido no se ha encontrado."));

        Mesa mesa = mesaRepository.findById(request.getIdmesa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La mesa relacionada al pedido no se ha encontrado."));

        Usuario usuario = usuarioRepository.findById(request.getIdusuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("El usuario relacionado al pedido no se ha encontrado"));

        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setEmpresa(empresa);
        pedido.setMesa(mesa);
        pedido.setUsuario(usuario);

        return pedidoMapper.toResponse(pedidoRepository.save(pedido));

    }

    @Override
    @Transactional
    public void delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El pedido que se intenta eliminar no se ha encontrado."));

        pedidoRepository.delete(pedido);
    }
}
