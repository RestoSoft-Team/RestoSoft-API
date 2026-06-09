package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.repository.ConfiguracionPaginaClienteRepository;

public interface ConfiguracionPaginaClienteService {

    public ConfiguracionPaginaClienteResponse findByEmpresaId(Integer idEmpresa);
    public ConfiguracionPaginaClienteResponse save(ConfiguracionPaginaClienteRequest request);
    public ConfiguracionPaginaClienteResponse update(ConfiguracionPaginaClienteRequest cliente, Integer id);
    public void deleteById(Integer id);

}
