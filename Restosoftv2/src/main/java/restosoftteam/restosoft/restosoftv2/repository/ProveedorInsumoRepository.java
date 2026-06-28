package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.ProveedorInsumo;

import java.util.List;
import java.util.Optional;

public interface ProveedorInsumoRepository extends JpaRepository<ProveedorInsumo, Integer> {

    Optional<ProveedorInsumo> findByIdAndProveedorEmpresaId (Integer id, Integer idEmpresa);
    List<ProveedorInsumo> findByProveedorId (Integer idProveedor);
    List<ProveedorInsumo> findByInsumoId(Integer idInsumo);
    void deleteByProveedorIdAndInsumoId(Integer idProveedor, Integer idInsumo);
    boolean existsByProveedorIdAndInsumoId(Integer proveedorId, Integer insumoId);
}