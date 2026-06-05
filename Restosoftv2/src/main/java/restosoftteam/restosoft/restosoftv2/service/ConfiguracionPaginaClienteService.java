package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;

public interface ConfiguracionPaginaClienteService {

    public ConfiguracionPaginaCliente findByEmpresaId (Integer idEmpresa);
    public ConfiguracionPaginaCliente save(ConfiguracionPaginaCliente cliente);
//    public ConfiguracionPaginaCliente update(ConfiguracionPaginaCliente cliente);
    public void deleteById(Integer id);

}
