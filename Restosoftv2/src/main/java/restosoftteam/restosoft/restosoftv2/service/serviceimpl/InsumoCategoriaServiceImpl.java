package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoCategoriaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoCategoriaResponse;
import restosoftteam.restosoft.restosoftv2.mapper.InsumoCategoriaMapper;
import restosoftteam.restosoft.restosoftv2.model.InsumoCategoria;
import restosoftteam.restosoft.restosoftv2.repository.InsumoCategoriaRepository;
import restosoftteam.restosoft.restosoftv2.service.InsumoCategoriaService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InsumoCategoriaServiceImpl implements InsumoCategoriaService {

    private final InsumoCategoriaRepository repository;
    private final InsumoCategoriaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<InsumoCategoriaResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(cat -> {
                    InsumoCategoriaResponse response = mapper.toResponse(cat);
                    response.setCantidadInsumos(
                            repository.contarInsumosPorCategoria(cat.getId())
                    );
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoCategoriaResponse buscarPorId(Integer id) {
        InsumoCategoria categoria = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrada con ID: " + id));
        InsumoCategoriaResponse response = mapper.toResponse(categoria);
        response.setCantidadInsumos(repository.contarInsumosPorCategoria(id));
        return response;
    }

    @Override
    @Transactional
    public InsumoCategoriaResponse crear(InsumoCategoriaRequest request) {
        if (repository.existsByNombre(request.getNombre())){
            throw new RuntimeException("Ya existe ...");
        }
        InsumoCategoria nuevaCategoria = mapper.toEntity(request);
        InsumoCategoria categoriaGuardada = repository.save(nuevaCategoria);
        return mapper.toResponse(categoriaGuardada);
    }

    @Override
    @Transactional
    public InsumoCategoriaResponse actualizar(Integer id, InsumoCategoriaRequest request) {
        InsumoCategoria categoriaExistente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se puede actualizar. Categoria no encontrada con el ID: " + id));
        mapper.updateEntity(request, categoriaExistente);

        InsumoCategoria categoriaActualizada = repository.save(categoriaExistente);
        return mapper.toResponse(categoriaActualizada);
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!repository.existsById(id)){
            throw new RuntimeException("No se puede eliminar. Categoria no encontrada con el ID: " + id);
        }
        repository.deleteById(id);
    }
}
