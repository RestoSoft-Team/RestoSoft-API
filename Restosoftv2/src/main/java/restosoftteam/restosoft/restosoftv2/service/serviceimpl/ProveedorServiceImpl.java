package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.mapper.ProveedorMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorRepository;
import restosoftteam.restosoft.restosoftv2.service.ProveedorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository repo;
    private final ProveedorMapper mapper;
    private final EmpresaRepository empresaRepository; // Inyectamos el repositorio de empresas

    @Override
    @Transactional
    public ProveedorResponse crear(ProveedorRequest request) {
        // 1. Buscar empresa
        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // 2. Mapear
        Proveedor p = mapper.toEntity(request);

        // 3. ASIGNAR (Forzamos la relación)
        p.setEmpresa(empresa);

        // DEPURACIÓN: Imprimir en consola para ver si tiene empresa antes de guardar
        System.out.println("DEBUG: Proveedor a guardar: " + p.getNombre());
        System.out.println("DEBUG: ¿Empresa es nula?: " + (p.getEmpresa() == null));

        // 4. Guardar
        Proveedor guardado = repo.save(p);

        return mapper.toResponse(guardado);
    }

    @Override @Transactional(readOnly = true)
    public List<ProveedorResponse> listar() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }

    @Override
    @Transactional
    public ProveedorResponse actualizar(Integer id, ProveedorRequest request) {
        Proveedor proveedor = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));

        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        // Actualiza los campos con el mapper
        mapper.updateEntityFromDto(request, proveedor);

        // Fuerza la relación igual que en crear()
        proveedor.setEmpresa(empresa);

        return mapper.toResponse(repo.save(proveedor));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("Proveedor no encontrado con ID: " + id);
        }
        repo.deleteById(id);
    }
}