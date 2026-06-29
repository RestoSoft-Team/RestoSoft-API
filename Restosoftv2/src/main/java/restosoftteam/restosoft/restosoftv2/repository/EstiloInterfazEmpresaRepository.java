package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;

import java.util.Optional;

public interface EstiloInterfazEmpresaRepository extends JpaRepository<EstiloInterfazEmpresa, Integer> {
    Optional<EstiloInterfazEmpresa> findByEmpresa_Id(Integer id);

}
