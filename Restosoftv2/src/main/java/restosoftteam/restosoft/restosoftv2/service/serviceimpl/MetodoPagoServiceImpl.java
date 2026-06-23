package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.response.MetodoPagoResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MetodoPagoMapper;
import restosoftteam.restosoft.restosoftv2.repository.MetodoPagoRepository;
import restosoftteam.restosoft.restosoftv2.service.MetodoPagoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetodoPagoServiceImpl implements MetodoPagoService {

    private final MetodoPagoRepository repo;
    private final MetodoPagoMapper mapper;

    @Override
    public List<MetodoPagoResponse> listar() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }
}
