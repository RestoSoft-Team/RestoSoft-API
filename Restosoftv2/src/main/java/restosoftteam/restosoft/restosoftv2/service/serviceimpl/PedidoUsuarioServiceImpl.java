package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PedidoUsuarioResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PedidoUsuarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Pedido;
import restosoftteam.restosoft.restosoftv2.model.PedidoUsuario;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.PedidoRepository;
import restosoftteam.restosoft.restosoftv2.repository.PedidoUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.UsuarioRepository;
import restosoftteam.restosoft.restosoftv2.service.PedidoUsuarioService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoUsuarioServiceImpl implements PedidoUsuarioService {


    private final PedidoUsuarioRepository pedidoUsuarioRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PedidoUsuarioMapper pedidoUsuarioMapper;


    @Override
    public PedidoUsuarioResponse findById(Integer id) {

        PedidoUsuario pedidoUsuario = pedidoUsuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se a asignado ningun usuario al pedido por el momento"));

        return pedidoUsuarioMapper.toResponse(pedidoUsuario);
    }

    @Override
    @Transactional
    public PedidoUsuarioResponse save(PedidoUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con Usuarios relacionados"));

        PedidoUsuario pedidoUsuario = pedidoUsuarioMapper.toEntity(request);
        pedidoUsuario.setUsuario(usuario);
        pedidoUsuario.setPedido(pedido);

        return pedidoUsuarioMapper.toResponse(pedidoUsuarioRepository.save(pedidoUsuario));
    }

    @Override
    public PedidoUsuarioResponse update(PedidoUsuarioRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));

        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado con Usuarios relacionados"));

        PedidoUsuario pedidoUsuario = pedidoUsuarioMapper.toEntity(request);
        pedidoUsuario.setUsuario(usuario);
        pedidoUsuario.setPedido(pedido);

        return pedidoUsuarioMapper.toResponse(pedidoUsuarioRepository.save(pedidoUsuario));
    }
}
