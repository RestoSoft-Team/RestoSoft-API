package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.DetallePedido;

import java.util.List;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Integer> {

    @Query("SELECT d FROM DetallePedido d JOIN FETCH d.plato p JOIN FETCH p.categoria WHERE d.pedido.id = :idPedido")
    List<DetallePedido> findAllByPedido_Id(@Param("idPedido") Integer idPedido);
}