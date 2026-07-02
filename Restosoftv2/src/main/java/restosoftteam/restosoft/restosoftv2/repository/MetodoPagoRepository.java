package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.MetodoPago;

public interface MetodoPagoRepository extends JpaRepository<MetodoPago, Integer> {
    boolean existsByNombre(String nombre);
}
