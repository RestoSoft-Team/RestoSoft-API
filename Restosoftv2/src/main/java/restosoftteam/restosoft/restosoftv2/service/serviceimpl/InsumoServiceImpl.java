package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.InsumoMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.InsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorRepository;
import restosoftteam.restosoft.restosoftv2.service.InsumoService;
import restosoftteam.restosoft.restosoftv2.model.InsumoCategoria;
import restosoftteam.restosoft.restosoftv2.repository.InsumoCategoriaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsumoServiceImpl implements InsumoService {

    private final InsumoRepository insumoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;
    private final InsumoCategoriaRepository insumoCategoriaRepository;
    private final InsumoMapper insumoMapper;

    @Override
    @Transactional
    public List<InsumoResponse> listarPorEmpresa(Integer idEmpresa) {
        return insumoRepository.findAllByEmpresaId(idEmpresa)
                .stream()
                .map(insumoMapper::toResponse)
                .toList();
    }

    @Override
    public InsumoResponse obtenerPorId(Integer id, Integer idEmpresa) {
        Insumo insumo = insumoRepository.findByIdAndEmpresaId(id, idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
        return insumoMapper.toResponse(insumo);
    }

    @Override
    @Transactional
    public InsumoResponse crear(Integer idEmpresa,InsumoRequest request) {
        if (insumoRepository.existsByNombreAndEmpresaId(request.getNombre(), request.getIdEmpresa())){
            throw new RegistroYaExistenteException("Ya existe un insumo con ese nombre en esta empresa");
        }
        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));

        Proveedor proveedor = null;
        if (request.getIdProveedor() != null){
            proveedor = proveedorRepository.findByIdAndEmpresaId(request.getIdProveedor(), request.getIdEmpresa())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado"));
        }

        InsumoCategoria categoria = null;
        if (request.getIdCategoria() != null){
            categoria = insumoCategoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
        }

        Insumo insumo = insumoMapper.toEntity(request);
        insumo.setEmpresa(empresa);
        insumo.setProveedor(proveedor);
        insumo.setCategoria(categoria);

        return insumoMapper.toResponse(insumoRepository.save(insumo));
    }

    @Override
    @Transactional
    public InsumoResponse actualizar(Integer id,Integer idEmpresa ,InsumoRequest request) {
        Insumo insumo = insumoRepository.findByIdAndEmpresaId(id, request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));

        Proveedor proveedor = null;
        if (request.getIdProveedor() != null){
            proveedor = proveedorRepository.findByIdAndEmpresaId(request.getIdProveedor(), request.getIdEmpresa())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado"));
        }

        InsumoCategoria categoria = null;
        if (request.getIdCategoria() != null){
            categoria = insumoCategoriaRepository.findById(request.getIdCategoria())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada"));
        }

        insumoMapper.updateEntityFromDto(request, insumo);
        insumo.setProveedor(proveedor);
        insumo.setCategoria(categoria);
        return insumoMapper.toResponse(insumoRepository.save(insumo));
    }

    @Override
    @Transactional
    public void eliminar(Integer id, Integer idEmpresa) {
        Insumo insumo = insumoRepository.findByIdAndEmpresaId(id, idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
        insumoRepository.delete(insumo);
    }
}
