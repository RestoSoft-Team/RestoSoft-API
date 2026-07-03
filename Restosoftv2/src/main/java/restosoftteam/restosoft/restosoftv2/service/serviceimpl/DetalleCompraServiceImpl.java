package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.response.DetalleCompraResponse;
import restosoftteam.restosoft.restosoftv2.mapper.DetalleCompraMapper;
import restosoftteam.restosoft.restosoftv2.repository.DetalleCompraRepository;
import restosoftteam.restosoft.restosoftv2.service.DetalleCompraService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleCompraServiceImpl implements DetalleCompraService {

    private final DetalleCompraRepository repo;
    private final DetalleCompraMapper mapper;


    @Override
    public List<DetalleCompraResponse> listarPorCompra(Integer idCompra) {
        return repo.findByCompraId(idCompra).stream().map(mapper::toResponse).toList();
    }
}
