package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.CierreCaja;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CierreCajaRepository extends JpaRepository<CierreCaja, Integer> {
    List<CierreCaja> findByEmpresa_Id(Integer idEmpresa);
    Optional<CierreCaja> findByEmpresa_IdAndFecha(Integer idEmpresa, LocalDate fecha);
}
