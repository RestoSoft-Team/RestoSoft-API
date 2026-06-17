package restosoftteam.restosoft.restosoftv2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;
import java.util.List;

public interface EmpresaUsuarioRepository extends JpaRepository<EmpresaUsuario, Integer> {

    List<EmpresaUsuario> findByIdempresaId(Integer idEmpresa);

    List<EmpresaUsuario> findByIdusuarioId(Integer idUsuario);
}