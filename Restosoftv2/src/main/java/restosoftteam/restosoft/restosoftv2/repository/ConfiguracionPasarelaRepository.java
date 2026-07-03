package restosoftteam.restosoft.restosoftv2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPasarela;

import java.util.Optional;

public interface ConfiguracionPasarelaRepository extends JpaRepository<ConfiguracionPasarela, Integer> {
    Optional<ConfiguracionPasarela> findByEmpresa_IdAndPasarela(Integer idEmpresa, String pasarela);
    Optional<ConfiguracionPasarela> findByEmpresa_IdAndPasarelaAndActivoTrue(Integer idEmpresa, String pasarela);
}