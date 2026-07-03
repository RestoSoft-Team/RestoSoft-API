package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface AsistenciaRepository extends JpaRepository<Asistencia, Integer> {

    List<Asistencia> findByIdusuarioIdAndIdempresaId(Integer idUsuario, Integer idEmpresa);

    List<Asistencia> findByIdempresaIdAndFechaEntradaBetween(
            Integer idEmpresa, Instant inicio, Instant fin);

    Optional<Asistencia> findFirstByIdusuarioIdAndIdempresaIdAndFechaSalidaIsNullOrderByFechaEntradaDesc(
            Integer idUsuario, Integer idEmpresa);

    Optional<Asistencia> findFirstByIdusuarioIdAndIdempresaIdOrderByIdDesc(
            Integer idUsuario, Integer idEmpresa);
}