package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ConfiguracionPaginaClienteMapper;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConfiguracionPaginaClienteServiceImpl implements ConfiguracionPaginaClienteService {
    private final ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository;
    private final ConfiguracionPaginaClienteMapper configuracionPaginaClienteMapper;
    private final EmpresaRepository empresaRepository;

    @Override
    public ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa) {

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByEmpresa_Id(idEmpresa);
        if (configuracion != null) {
            return configuracionPaginaClienteMapper.toResponse(configuracion);
        }
        throw new RecursoNoEncontradoException("Configuracion no encontrada");

    }

    @Override
    @Transactional
    public ConfiguracionPaginaClienteResponse save(ConfiguracionPaginaClienteRequest request) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa relacionada a la configuracion no encontrada"));

        boolean existe = configuracionPaginaClienteRepository.existsByEmpresa_Id(request.getIdempresa());
        if (existe) {
            throw new RegistroYaExistenteException("La configuracion de pagina cliente ya existe");
        }

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteMapper.toEntity(request);
        configuracion.setEmpresa(empresa);

        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }


    @Override
    @Transactional
    public ConfiguracionPaginaClienteResponse update(ConfiguracionPaginaClienteRequest request, Integer id) {

        Empresa empresa = empresaRepository.findById(request.getIdempresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa relacionada a la configuracion no encontrada"));

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La configuracion de pagina cliente que se intenta actualizar no existe."));


        configuracionPaginaClienteMapper.updateEntityFromDto(request, configuracion);

        configuracion.setEmpresa(empresa);
        configuracion.setId(id);

        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }


    @Override
    @Transactional
    public void deleteById(Integer id) {

        boolean existe = configuracionPaginaClienteRepository.existsById(id);
        if (!existe) {
            throw new RecursoNoEncontradoException("La configuracion de pagina cliente que se intenta eliminar no existe");
        }
        configuracionPaginaClienteRepository.deleteById(id);

    }


}
