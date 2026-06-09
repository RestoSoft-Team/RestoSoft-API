package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.ConfiguracionPaginaClienteMapper;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;
import restosoftteam.restosoft.restosoftv2.repository.PedidoRepository;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;

import java.util.Optional;

public class ConfiguracionPaginaClienteServiceImpl implements ConfiguracionPaginaClienteService {
//    private final ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository;
//    private final ConfiguracionPaginaClienteMapper configuracionPaginaClienteMapper;
//
//    public ConfiguracionPaginaClienteServiceImpl(ConfiguracionPaginaClienteRepository configuracionPaginaClienteRepository) {
//        this.configuracionPaginaClienteRepository = configuracionPaginaClienteRepository;
//    }
//
////    public ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa) {
////        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteRepository.findByIdempresa_Id(idEmpresa);
////        return configuracionPaginaClienteMapper.toResponse(configuracion);
////
////    }
//
//    public ConfiguracionPaginaClienteResponse save(ConfiguracionPaginaClienteRequest request) {
//
//        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteMapper.toEntity(request);
//        boolean existe = configuracionPaginaClienteRepository.existsByIdEmpresa(configuracion.getId());
//        if(existe){
//            throw new RegistroYaExistenteException("La configuracion de pagina cliente ya existe");
//        }
//        return configuracionPaginaClienteMapper.toResponse( configuracionPaginaClienteRepository.save(configuracion));
//
//    }
//
//        public ConfiguracionPaginaClienteResponse update(ConfiguracionPaginaClienteRequest cliente, Integer id) {
//
//        ConfiguracionPaginaCliente configuracion = configuracionPaginaClienteMapper.toEntity(cliente);
//        return configuracionPaginaClienteMapper.toResponse(configuracionPaginaClienteRepository.save(configuracion));
//
//        }
//
////    Implementar dtos y mappers queda aqui <<<<<<<<<<<<<<<<<<<<<
//
//    @Transactional
//    public void deleteById(Integer id) {
//        ConfiguracionPaginaCliente configuracionPaginaCliente = configuracionPaginaClienteRepository.findById(id)
//                .orElseThrow(() -> new RecursoNoEncontradoException("Configuracion no encontrada, no se puede eliminar"));
//        configuracionPaginaClienteRepository.deleteById(id);
//    }


}
