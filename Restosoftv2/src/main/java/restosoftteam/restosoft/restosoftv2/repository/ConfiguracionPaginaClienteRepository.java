package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;

public interface ConfiguracionPaginaClienteRepository extends JpaRepository<ConfiguracionPaginaCliente, Integer> {
    ConfiguracionPaginaCliente findByIdempresa_Id(Integer idempresa);
}
