package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.EstiloInterfazEmpresa;

public interface EstiloInterfazEmpresaRepository extends JpaRepository<EstiloInterfazEmpresa, Integer> {
    EstiloInterfazEmpresa findByIdEmpresa_Id(Integer id);
}
