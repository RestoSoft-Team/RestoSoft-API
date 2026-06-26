package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.PedidoMesa;

import java.util.List;

public interface PedidoMesaRepository extends JpaRepository<PedidoMesa, Integer> {
    List<PedidoMesa> findByMesa_Id(Integer idMesa);
}