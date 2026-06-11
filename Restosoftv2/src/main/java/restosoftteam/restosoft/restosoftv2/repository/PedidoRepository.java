package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.dto.request.PedidoRequest;
import restosoftteam.restosoft.restosoftv2.model.Pedido;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findAllByIdEmpresa_Id(Integer id);

}
