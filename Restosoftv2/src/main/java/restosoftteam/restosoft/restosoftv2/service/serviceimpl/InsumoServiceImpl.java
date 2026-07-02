package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.InsumoMapper;
import restosoftteam.restosoft.restosoftv2.model.*;
import restosoftteam.restosoft.restosoftv2.repository.*;
import restosoftteam.restosoft.restosoftv2.service.InsumoService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;
    private final InsumoCategoriaRepository insumoCategoriaRepository;
    private final InsumoMapper insumoMapper;
    private final ProveedorInsumoRepository proveedorInsumoRepository;

    @Override
    @Transactional
    public List<InsumoResponse> listarPorEmpresa(Integer idEmpresa) {
        return insumoRepository.findAllByEmpresaId(idEmpresa)
                .stream()
                .map(insumoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InsumoResponse obtenerPorId(Integer id, Integer idEmpresa) {
        Insumo insumo = insumoRepository.findByIdAndEmpresaId(id, idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
        return insumoMapper.toResponse(insumo);
    }

    @Override
    @Transactional
    public InsumoResponse crear(InsumoRequest request, Integer idEmpresa) {
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        InsumoCategoria insumoCategoria = insumoCategoriaRepository.findById(request.getIdCategoria())
                .orElseThrow(()->new RecursoNoEncontradoException("categoria no encontrada"));

        Insumo insumo = insumoMapper.toEntity(request);

        insumo.setEmpresa(empresa); // Forzamos la relación
        insumo.setCategoria(insumoCategoria);
        Insumo guardado = insumoRepository.save(insumo);

        // Lógica para guardar el proveedor en la tabla intermedia
        if (request.getIdProveedor() != null) {
            Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            ProveedorInsumo pi = new ProveedorInsumo();
            pi.setInsumo(guardado);
            pi.setProveedor(proveedor);
            proveedorInsumoRepository.save(pi);
        }

        return insumoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public InsumoResponse actualizar(Integer id, InsumoRequest request, Integer idEmpresa) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        insumoMapper.updateEntityFromDto(request, insumo);
        insumo.setEmpresa(empresa); // Forzamos la relación
        Insumo guardado = insumoRepository.save(insumo);

        // Lógica para actualizar el proveedor en la tabla intermedia
        if (request.getIdProveedor() != null) {
            // 1. Borramos las relaciones anteriores que tenía este insumo
            proveedorInsumoRepository.deleteAllByInsumoId(guardado.getId());

            // 2. Creamos la nueva relación con el proveedor seleccionado
            Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));

            ProveedorInsumo pi = new ProveedorInsumo();
            pi.setInsumo(guardado);
            pi.setProveedor(proveedor);
            proveedorInsumoRepository.save(pi);
        }

        return insumoMapper.toResponse(guardado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id, Integer idEmpresa) {
        Insumo insumo = insumoRepository.findByIdAndEmpresaId(id, idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
        insumoRepository.delete(insumo);
    }
}