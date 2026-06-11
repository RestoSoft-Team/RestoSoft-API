package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.EmpresaMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.service.EmpresaService;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    @Override
    public EmpresaResponse findById(Integer id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa no existe."));

        return empresaMapper.toResponse(empresa);
    }
    @Override
    public List<EmpresaResponse> findAll() {
        return empresaRepository.findAll()
                .stream().map(empresaMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public EmpresaResponse save(EmpresaRequest request) {
        return empresaMapper.toResponse(
                empresaRepository.save(empresaMapper.toEntity(request)));
    }

    @Override
    @Transactional
    public EmpresaResponse update(EmpresaRequest request, Integer id) {

        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La empresa que se intenta actualizar no existe"));


        empresaMapper.updateEntityFromDto(request, empresa);

        return empresaMapper.toResponse(
                empresaRepository.save(empresa));


    }

    @Override
    @Transactional
    public void delete(Integer id){
        Empresa empresa = empresaRepository.findById(id).orElseThrow(()-> new RecursoNoEncontradoException("La empresa que se intenta eliminar no existe."));
        empresaRepository.delete(empresa);
    }
}
