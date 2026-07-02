package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorInsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorInsumoResponse;
import restosoftteam.restosoft.restosoftv2.mapper.ProveedorInsumoMapper;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;
import restosoftteam.restosoft.restosoftv2.model.ProveedorInsumo;
import restosoftteam.restosoft.restosoftv2.repository.InsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorInsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorRepository;
import restosoftteam.restosoft.restosoftv2.service.ProveedorInsumoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorInsumoServiceImpl implements ProveedorInsumoService {

    private final ProveedorInsumoRepository repo;
    private final ProveedorInsumoMapper mapper;
    private final ProveedorRepository proveedorRepository;
    private final InsumoRepository insumoRepository;

    @Override
    @Transactional
    public ProveedorInsumoResponse crear(ProveedorInsumoRequest request) {

        Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Insumo insumo = insumoRepository.findById(request.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        if (repo.existsByProveedorIdAndInsumoId(proveedor.getId(), insumo.getId())) {
            throw new RuntimeException("Este proveedor ya está asociado a ese insumo.");
        }

        ProveedorInsumo entidad = mapper.toEntity(request);

        entidad.setProveedor(proveedor);
        entidad.setInsumo(insumo);

        ProveedorInsumo guardado = repo.save(entidad);

        return mapper.toResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorInsumoResponse> listar() {
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProveedorInsumoResponse actualizar(Integer id, ProveedorInsumoRequest request) {

        ProveedorInsumo entidad = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Relación proveedor-insumo no encontrada con ID: " + id));

        Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

        Insumo insumo = insumoRepository.findById(request.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        if (repo.existsByProveedorIdAndInsumoId(proveedor.getId(), insumo.getId())
                && (!entidad.getProveedor().getId().equals(proveedor.getId())
                || !entidad.getInsumo().getId().equals(insumo.getId()))) {

            throw new RuntimeException("Ya existe esa relación entre proveedor e insumo.");
        }

        mapper.updateEntityFromDto(request, entidad);

        entidad.setProveedor(proveedor);
        entidad.setInsumo(insumo);

        return mapper.toResponse(repo.save(entidad));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {

        if (!repo.existsById(id)) {
            throw new RuntimeException("Relación proveedor-insumo no encontrada con ID: " + id);
        }

        repo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorInsumoResponse> listarPorProveedor(Integer idProveedor) {

        return repo.findByProveedorId(idProveedor)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorInsumoResponse> listarPorInsumo(Integer idInsumo) {

        return repo.findByInsumoId(idInsumo)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}