package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;

import java.util.List;
import java.util.Optional;

public interface EmpresaUsuarioRepository extends JpaRepository<EmpresaUsuario, Integer> {

    @Query("""
            SELECT eu
            FROM EmpresaUsuario eu
                JOIN FETCH eu.usuario u
                JOIN FETCH eu.empresa e
                JOIN FETCH eu.rol r
            WHERE eu.id = :id
            """)
    Optional<EmpresaUsuario> buscarCompletoPorId(
            @Param("id") Integer id
    );

    @Query("""
            SELECT eu
            FROM EmpresaUsuario eu
                JOIN FETCH eu.usuario u
                JOIN FETCH eu.empresa e
                JOIN FETCH eu.rol r
            WHERE e.id = :empresaId
            ORDER BY u.nombres
            """)
    List<EmpresaUsuario> listarPorEmpresa(
            @Param("empresaId") Integer empresaId
    );

    @Query("""
            SELECT eu
            FROM EmpresaUsuario eu
                JOIN FETCH eu.usuario u
                JOIN FETCH eu.empresa e
                JOIN FETCH eu.rol r
            WHERE u.id = :usuarioId
              AND e.id = :empresaId
            """)
    Optional<EmpresaUsuario> buscarPorUsuarioYEmpresa(
            @Param("usuarioId") Integer usuarioId,
            @Param("empresaId") Integer empresaId
    );

    @Query("""
            SELECT eu
            FROM EmpresaUsuario eu
                JOIN FETCH eu.usuario u
                JOIN FETCH eu.empresa e
                JOIN FETCH eu.rol r
            WHERE u.email = :email
            """)
    Optional<EmpresaUsuario> buscarPorEmail(
            @Param("email") String email
    );

    boolean existsByUsuarioIdAndEmpresaId(
            Integer usuarioId,
            Integer empresaId
    );

    void deleteByUsuarioIdAndEmpresaId(
            Integer usuarioId,
            Integer empresaId
    );

    long countByUsuarioId(Integer usuarioId);

}