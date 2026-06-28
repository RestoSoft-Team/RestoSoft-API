package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restosoftteam.restosoft.restosoftv2.model.Plato;

import java.util.List;

@Repository
public interface PlatoRepository extends JpaRepository<Plato, Integer> {

    List<Plato> findAllByEmpresa_Id(Integer idEmpresa);

    @Query("SELECT p FROM Plato p JOIN FETCH p.categoria JOIN FETCH p.empresa WHERE p.empresa.id = :idEmpresa")
    List<Plato> findAllConDetallesByEmpresa_Id(@Param("idEmpresa") Integer idEmpresa);
}