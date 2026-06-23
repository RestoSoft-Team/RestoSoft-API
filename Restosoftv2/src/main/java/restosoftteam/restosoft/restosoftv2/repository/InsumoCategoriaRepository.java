package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.InsumoCategoria;

import java.util.List;
import java.util.Optional;


public interface InsumoCategoriaRepository extends JpaRepository <InsumoCategoria, Integer>{

    Optional<InsumoCategoria> findByNombre(String nombre);

    List<InsumoCategoria> findByNombreContainingIgnoreCase(String palabra);

    boolean existsByNombre(String nombre);
}
