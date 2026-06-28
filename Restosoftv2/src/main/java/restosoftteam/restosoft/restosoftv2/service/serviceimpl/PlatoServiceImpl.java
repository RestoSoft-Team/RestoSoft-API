package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PlatoMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Plato;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoCategoriaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoRepository;
import restosoftteam.restosoft.restosoftv2.service.PlatoService;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlatoServiceImpl implements PlatoService {
    private final PlatoRepository platoRepository;
    private final PlatoMapper platoMapper;
    private final EmpresaRepository empresaRepository;
    private final PlatoCategoriaRepository platoCategoriaRepository;

    @Override
    public PlatoResponse findById(Integer id) {

        return platoMapper.toResponse(platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato no se ha encontrado.")));


    }

    @Override
    public List<PlatoResponse> findAllByEmpresaId(Integer idEmpresa) {
        return platoRepository.findAllConDetallesByEmpresa_Id(idEmpresa)
                .stream().map(platoMapper::toResponse).toList();
    }

    @Override
    @Transactional
    public PlatoResponse update(PlatoRequest request, Integer id) {

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato que se intenta actualizar no existe."));

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        PlatoCategoria platoCategoria = platoCategoriaRepository.findById(request.getIdcategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("La categoria del plato relacionada no existe."));

        platoMapper.updateEntityFromDto(request, plato);
        plato.setEmpresa(empresa);
        plato.setCategoria(platoCategoria);

        return platoMapper.toResponse(platoRepository.save(plato));

    }

    @Override
    @Transactional
    public PlatoResponse save(PlatoRequest request) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada no existe."));

        PlatoCategoria platoCategoria = platoCategoriaRepository.findById(request.getIdcategoria())
                .orElseThrow(() -> new RecursoNoEncontradoException("La categoria del plato relacionada no existe."));

        Plato plato = platoMapper.toEntity(request);
        plato.setEmpresa(empresa);
        plato.setCategoria(platoCategoria);
        return platoMapper.toResponse(platoRepository.save(plato));

    }

    @Override
    @Transactional
    public void delete(Integer id) {

        Plato plato = platoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El plato que se intenta eliminar no existe."));
        platoRepository.delete(plato);

    }
}
