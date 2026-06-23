package restosoftteam.restosoft.restosoftv2.service.serviceimpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.mapper.MovimientoInventarioMapper;
import restosoftteam.restosoft.restosoftv2.repository.MovimientoInventarioRepository;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository repo;
    private final MovimientoInventarioMapper mapper;


    @Override
    public List<MovimientoInventarioResponse> listar() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }
}
