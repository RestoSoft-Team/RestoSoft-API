package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restosoftteam.restosoft.restosoftv2.model.Mesa;

import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    boolean existsByNumeroMesaAndIdempresa_Id(String numeroMesa, Integer idempresaId);

    Optional<Mesa> findByIdAndIdempresa_Id(Integer id, Integer idempresa);

    void deleteByIdAndIdempresa_Id(Integer id, Integer idempresaId);

    boolean existsByEstadoAndIdempresa_Id(String estado, Integer idempresaId);

}