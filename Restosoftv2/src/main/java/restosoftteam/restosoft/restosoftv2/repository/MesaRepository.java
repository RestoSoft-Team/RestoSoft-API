package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restosoftteam.restosoft.restosoftv2.model.Mesa;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    boolean existsByNumeroMesaAndEmpresa_Id(String numeroMesa, Integer idempresaId);

    Optional<Mesa> findByIdAndEmpresa_Id(Integer id, Integer empresaId, Limit limit);

    void deleteByIdAndEmpresa_Id(Integer id, Integer empresaId);

    boolean existsByEstadoAndEmpresa_Id(String estado, Integer empresaId);

    boolean existsBynumeroMesaAndEmpresa_IdAndIdNot(String numeroMesa, Integer empresaId, Integer idMesa);

    List<Mesa> findAllById(Integer id);
    List<Mesa> findAllByEmpresa_Id(Integer idEmpresa);

}