package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.mapper.ProveedorMapper;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorRepository;
import restosoftteam.restosoft.restosoftv2.service.ProveedorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository repo;
    private final ProveedorMapper mapper;

    @Override
    @Transactional
    public ProveedorResponse crear(ProveedorRequest request) {
        Proveedor p = mapper.toEntity(request);
        return mapper.toResponse(repo.save(p));
    }

    @Override @Transactional(readOnly = true)
    public List<ProveedorResponse> listar() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }
}
