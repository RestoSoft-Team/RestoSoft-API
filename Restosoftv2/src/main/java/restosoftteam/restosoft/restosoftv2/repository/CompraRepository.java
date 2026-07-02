package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Compra;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByEmpresaId(Integer idEmpresa);
    Optional<Compra> findByIdAndEmpresaId(Integer id, Integer idEmpresa);
}
