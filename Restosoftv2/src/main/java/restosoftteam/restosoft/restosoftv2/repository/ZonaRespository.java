package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.model.Zona;

import java.util.List;
import java.util.Optional;


@Repository
public interface ZonaRespository extends JpaRepository<Zona, Integer> {
    @Modifying
    @Transactional
    void deleteByIdAndIdEmpresa_Id(Integer id, Integer idEmpresa);

    Optional<Zona> findByIdAndIdEmpresa_Id(Integer id, Integer idEmpresa);

    List<Zona> findAllByIdEmpresa_Id(Integer idEmpresa);
}
