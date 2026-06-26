package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;
import java.util.List;
import java.util.Optional;

public interface EmpresaUsuarioRepository extends JpaRepository<EmpresaUsuario, Integer> {

    List<EmpresaUsuario> findByIdempresaId(Integer idEmpresa);

    List<EmpresaUsuario> findByIdusuarioId(Integer idUsuario);

    @Query("SELECT eu FROM EmpresaUsuario eu" +
            " JOIN FETCH eu.usuario u JOIN FETCH" +
            " eu.empresa e JOIN FETCH eu.rol r WHERE u.email = :email")
    Optional<EmpresaUsuario> buscarPorEmail(@Param("email") String email);

}