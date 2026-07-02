package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.MovimientoInventario;

import java.util.List;

public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {
    List<MovimientoInventario> findByInsumoIdAndEmpresaId(Integer idInsumo, Integer idEmpresa);
    List<MovimientoInventario> findAllByEmpresaId(Integer idEmpresa);
}
