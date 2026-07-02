package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.DetalleCompra;

import java.util.List;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer> {
    List<DetalleCompra> findByCompraId(Integer idCompra);
}
