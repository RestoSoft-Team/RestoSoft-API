package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restosoftteam.restosoft.restosoftv2.model.Promocion;

import java.util.List;

public interface PromocionRepository extends JpaRepository<Promocion, Integer> {
    List<Promocion> findByIdempresa_Id(Integer idEmpresa);
    @Query("SELECT p FROM Promocion p WHERE p.fechaInicio <= CURRENT_DATE AND p.fechaFin >= CURRENT_DATE AND p.estado = 'ACTIVA'")
    List<Promocion> findPromocionesVigentes();
}
