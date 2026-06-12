package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.EstiloInterfazEmpresaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EstiloInterfazEmpresaRepository;
import restosoftteam.restosoft.restosoftv2.service.EstiloInterfazEmpresaService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstiloInterfazEmpresaServiceImpl implements EstiloInterfazEmpresaService {
    private final EstiloInterfazEmpresaRepository estiloInterfazEmpresaRepository;
    private final EstiloInterfazEmpresaMapper estiloInterfazEmpresaMapper;
    private final EmpresaRepository empresaRepository;

    @Override
    public EstiloInterfazEmpresaResponse findByIdEmpresa(Integer id) {

        EstiloInterfazEmpresa estilo = estiloInterfazEmpresaRepository.findByEmpresa_Id(id);
        if (estilo != null) {
            return estiloInterfazEmpresaMapper.toResponse(estilo);
        }
        throw new RecursoNoEncontradoException("Los estilos de interfaz de la empresa no existen.");

    }

    @Override
    @Transactional
    public EstiloInterfazEmpresaResponse save(EstiloInterfazEmpresaRequest request) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada a los estilos de interfaz no existe"));

        EstiloInterfazEmpresa estilo = estiloInterfazEmpresaMapper.toEntity(request);
        estilo.setEmpresa(empresa);

        return estiloInterfazEmpresaMapper.toResponse(
                estiloInterfazEmpresaRepository.save(estilo));

    }

    @Override
    @Transactional
    public EstiloInterfazEmpresaResponse update(EstiloInterfazEmpresaRequest request, Integer id) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa relacionada a los estilos de interfaz no existe"));

        EstiloInterfazEmpresa estilo = estiloInterfazEmpresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El estilo de interfaz que se intenta actualizar no existe."));

        estiloInterfazEmpresaMapper.updateEntityFromDto(request, estilo);

        estilo.setEmpresa(empresa);
        return estiloInterfazEmpresaMapper.toResponse(
                estiloInterfazEmpresaRepository.save(estilo));

    }

    @Override
    @Transactional
    public void delete(Integer id) {

        EstiloInterfazEmpresa estilo = estiloInterfazEmpresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("El estilo de interfaz que se intenta eliminar no existe."));
        estiloInterfazEmpresaRepository.delete(estilo);

    }
}
