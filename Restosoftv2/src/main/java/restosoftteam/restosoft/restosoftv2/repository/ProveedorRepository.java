package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository <Proveedor, Integer> {
    Optional<Proveedor> findByIdAndEmpresaId (Integer id, Integer idEmpresa);
    boolean existsByNombreAndEmpresaId(String nombre, Integer idEmpresa);
    List<Proveedor> findByEmpresaId(Integer idEmpresa);
}
