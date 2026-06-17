package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;

import java.util.List;

public interface PlatoCategoriaRepository extends JpaRepository<PlatoCategoria, Integer> {
    @Query("SELECT c FROM PlatoCategoria c WHERE c.idempresa.id = :idEmpresa")
    List<PlatoCategoria> findByEmpresaId(@Param("idEmpresa") Integer idEmpresa);
}
