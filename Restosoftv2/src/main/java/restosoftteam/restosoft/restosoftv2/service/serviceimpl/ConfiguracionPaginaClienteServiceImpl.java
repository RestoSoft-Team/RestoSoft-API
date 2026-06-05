package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;

import java.util.Optional;

public class ConfiguracionPaginaClienteServiceImpl implements ConfiguracionPaginaClienteService {
    private final ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository;

    public ConfiguracionPaginaClienteServiceImpl(ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository) {
        this.configuracionPaginaClienteRepository = configuracionPaginaClienteRepository;
    }

    public ConfiguracionPaginaCliente findByEmpresaId(Integer idEmpresa) {
        return Optional.ofNullable(configuracionPaginaClienteRepository.findByIdempresa_Id(idEmpresa))
                .orElseThrow(() -> new RecursoNoEncontradoException("Configuración no encontrada para la empresa con ID: " + idEmpresa));
    }

    public ConfiguracionPaginaCliente save(ConfiguracionPaginaCliente cliente) {
        return configuracionPaginaClienteRepository.save(cliente);
    }

//        public ConfiguracionPaginaCliente update(ConfiguracionPaginaCliente cliente){
//
//        }
//    Implementar dtos y mappers queda aqui <<<<<<<<<<<<<<<<<<<<<

    @Transactional
    public void deleteById(Integer id) {
        ConfiguracionPaginaCliente configuracionPaginaCliente = configuracionPaginaClienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Configuracion no encontrada, no se puede eliminar"));
        configuracionPaginaClienteRepository.deleteById(id);
    }


}
