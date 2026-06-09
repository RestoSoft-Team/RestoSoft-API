package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ConfiguracionPaginaClienteMapper;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;


@Service
@RequiredArgsConstructor
public class ConfiguracionPaginaClienteServiceImpl implements ConfiguracionPaginaClienteService {
    private final ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository;
    private final ConfiguracionPaginaClienteMapper configuracionPaginaClienteMapper;

    @Override
    public ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa) {

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByIdEmpresa_Id(idEmpresa);
        if (configuracion != null) {
            return configuracionPaginaClienteMapper.toResponse(configuracion);
        }
        throw new RecursoNoEncontradoException("Configuracion no encontrada");

    }

    @Override
    public ConfiguracionPaginaClienteResponse save(ConfiguracionPaginaClienteRequest request) {

        boolean existe = configuracionPaginaClienteRepository.existsByIdEmpresa_Id((request.getIdempresa());
        if (existe) {
            throw new RegistroYaExistenteException("La configuracion de pagina cliente ya existe");
        }
        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteMapper.toEntity(request);

        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }



    @Override
    public ConfiguracionPaginaClienteResponse update(ConfiguracionPaginaClienteRequest request, Integer id) {

        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("La configuracion de pagina cliente que se intenta actualizar no existe."));

        configuracionPaginaClienteMapper.updateEntityFromDto(request,configuracion);
        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));

    }


    @Override
    public void deleteById(Integer id) {

        boolean existe = configuracionPaginaClienteRepository.existsById(id);
        if (!existe) {
            throw new RecursoNoEncontradoException("La configuracion de pagina cliente que se intenta eliminar no existe");
        }
        configuracionPaginaClienteRepository.deleteById(id);

    }


}
