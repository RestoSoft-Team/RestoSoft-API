package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;
import java.time.Instant;
import java.util.List;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    List<Asistencia> findByIdusuarioIdAndIdempresaId(Integer idUsuario, Integer idEmpresa);

    // Para reportes
    List<Asistencia> findByIdempresaIdAndFechaEntradaBetween(
            Integer idEmpresa, Instant inicio, Instant fin);
}