package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.dto.response.PlatoResponse;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.util.List;

public interface PlatoRepository extends JpaRepository<Plato, Integer> {
    List<Plato> findAllByEmpresa_Id(Integer idEmpresa);
}
