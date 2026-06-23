package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {


    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u from Usuario u WHERE u.rol.nombre = 'CLIENTE'")
    List<Usuario> findAllByRolCliente();

}

