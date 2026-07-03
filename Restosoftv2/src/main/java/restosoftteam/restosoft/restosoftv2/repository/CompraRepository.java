package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Compra;
import java.time.Instant;
import java.util.List;

public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByIdempresa_Id(Integer idEmpresa);
    List<Compra> findByFechaBetween(Instant start, Instant end);
}
