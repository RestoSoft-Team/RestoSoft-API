package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.PedidoUsuario;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface PedidoUsuarioRepository extends JpaRepository<PedidoUsuario,Integer> {
    List<PedidoUsuario> findPedidoUsuarioById(Integer id);
    Optional<List<PedidoUsuario>> findAllByUsuario (Usuario usuario);
}
