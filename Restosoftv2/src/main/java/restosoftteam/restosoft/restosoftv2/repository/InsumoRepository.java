package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.Insumo;

import java.util.List;
import java.util.Optional;

public interface InsumoRepository extends JpaRepository <Insumo, Integer> {

    boolean existsByNombreAndEmpresaId(String nombre, Integer idEmpresa);

    Optional<Insumo> findByIdAndEmpresaId (Integer id, Integer IdEmpresa);

    @Query("SELECT i FROM Insumo i LEFT JOIN FETCH i.proveedores JOIN FETCH i.empresa WHERE i.empresa.id = :idEmpresa")
    List<Insumo> findAllByEmpresaId(Integer idEmpresa);
}