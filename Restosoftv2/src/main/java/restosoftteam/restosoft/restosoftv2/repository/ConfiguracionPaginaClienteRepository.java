package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;
import restosoftteam.restosoft.restosoftv2.model.Empresa;

import java.util.Optional;

public interface ConfiguracionPaginaClienteRepository extends JpaRepository<ConfiguracionPaginaCliente, Integer> {

    ConfiguracionPaginaCliente findByEmpresa_Id(Integer idEmpresa);

    boolean existsByEmpresa(Empresa empresa);

    Optional<ConfiguracionPaginaCliente> findByEmpresa(Empresa empresa);

    @Query("""
    SELECT cp
    FROM ConfiguracionPaginaCliente cp
    WHERE LOWER(cp.empresa.nombre) LIKE LOWER(CONCAT('%', :nombreEmpresa, '%'))
    """)
    Optional<ConfiguracionPaginaCliente> findByEmpresaNombre(@Param("nombreEmpresa") String nombreEmpresa);

    Optional<ConfiguracionPaginaCliente> findByEmpresa_Nombre(String idEmpresa);
}
