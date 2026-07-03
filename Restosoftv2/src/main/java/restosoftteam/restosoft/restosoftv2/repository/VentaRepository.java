package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.Venta;
import java.time.Instant;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {
    List<Venta> findByIdempresa_Id(Integer idEmpresa);
    List<Venta> findByFechaBetween(Instant inicio, Instant fin);
    java.util.Optional<Venta> findByIdpedido_Id(Integer idPedido);

    @Query("SELECT SUM(v.valorTotal) FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin AND v.idempresa.id = :idEmpresa")
    Double sumTotalByDateRange(@Param("inicio") Instant inicio,
                               @Param("fin") Instant fin,
                               @Param("idEmpresa") Integer idEmpresa);
}