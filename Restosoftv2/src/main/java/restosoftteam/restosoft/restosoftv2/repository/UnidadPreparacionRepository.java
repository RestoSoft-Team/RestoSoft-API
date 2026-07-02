package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.UnidadPreparacion;
import java.util.List;

public interface UnidadPreparacionRepository extends JpaRepository<UnidadPreparacion, Integer> {
    List<UnidadPreparacion> findAllByDetallePedido_Pedido_Id(Integer idPedido);
}