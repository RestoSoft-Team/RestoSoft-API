package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {
    DetallePedido findByPedido_Id(Integer id);
    List<DetallePedido> findAllByPedido_Id(Integer id);
}
