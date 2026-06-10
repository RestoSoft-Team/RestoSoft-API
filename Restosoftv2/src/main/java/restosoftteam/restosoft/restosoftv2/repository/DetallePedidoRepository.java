package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    DetallePedido findByIdPedido_Id(Integer id);
}
