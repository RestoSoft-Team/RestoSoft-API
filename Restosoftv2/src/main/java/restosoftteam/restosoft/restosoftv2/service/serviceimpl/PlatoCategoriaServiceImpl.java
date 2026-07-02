package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.PlatoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.PlatoCategoriaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.PlatoCategoriaRepository;
import restosoftteam.restosoft.restosoftv2.service.PlatoCategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlatoCategoriaServiceImpl implements PlatoCategoriaService {
    private final PlatoCategoriaRepository categoriaRepository;
    private final EmpresaRepository empresaRepository;
    private final PlatoCategoriaMapper mapper;

    @Override
    @Transactional
    public PlatoCategoriaResponse save(PlatoCategoriaRequest request, Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));
        PlatoCategoria categoria = mapper.toEntity(request);
        categoria.setIdempresa(empresa);
        return mapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public PlatoCategoriaResponse update(Integer id, PlatoCategoriaRequest request) {
        PlatoCategoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
        mapper.updateEntityFromDto(request, categoria);
        return mapper.toResponse(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!categoriaRepository.existsById(id))
            throw new RecursoNoEncontradoException("Categoría no encontrada");
        categoriaRepository.deleteById(id);
    }

    @Override
    public PlatoCategoriaResponse findById(Integer id) {
        return mapper.toResponse(categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlatoCategoriaResponse> findAllByEmpresa(Integer idEmpresa) {
        return categoriaRepository.findByEmpresaId(idEmpresa)
                .stream().map(mapper::toResponse).collect(Collectors.toList());
    }
}