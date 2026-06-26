package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.exception.RegistroYaExistenteException;
import restosoftteam.restosoft.restosoftv2.mapper.EmpresaUsuarioMapper;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.model.Usuario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaUsuarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.RolRepository;
import restosoftteam.restosoft.restosoftv2.service.EmpresaUsuarioService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class EmpresaUsuarioServiceImpl implements EmpresaUsuarioService {

    private final EmpresaUsuarioRepository empresaUsuarioRepository;

    private final EmpresaRepository empresaRepository;

    private final RolRepository rolRepository;

    private final EmpresaUsuarioMapper empresaUsuarioMapper;

    @Override
    public UsuarioCompletoResponse crearEmpleado(
            Usuario usuario,
            Integer idEmpresa,
            Integer idRol) {

        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "La empresa no existe."
                        )
                );

        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "El rol no existe."
                        )
                );

        if (empresaUsuarioRepository.existsByUsuarioIdAndEmpresaId(
                usuario.getId(),
                empresa.getId())) {

            throw new RegistroYaExistenteException(
                    "El usuario ya pertenece a esta empresa."
            );

        }

        EmpresaUsuario empresaUsuario = new EmpresaUsuario();

        empresaUsuario.setUsuario(usuario);
        empresaUsuario.setEmpresa(empresa);
        empresaUsuario.setRol(rol);

        EmpresaUsuario guardado =
                empresaUsuarioRepository.save(empresaUsuario);

        return construirResponse(guardado);

    }

    @Override
    public UsuarioCompletoResponse obtenerEmpleado(
            Integer idEmpresa,
            Integer idUsuario) {

        EmpresaUsuario empresaUsuario = empresaUsuarioRepository
                .buscarPorUsuarioYEmpresa(idUsuario, idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la relación empresa-usuario."
                ));

        return construirResponse(empresaUsuario);
    }

    @Override
    public List<UsuarioCompletoResponse> listarEmpleados(
            Integer idEmpresa) {

        return empresaUsuarioRepository.listarPorEmpresa(idEmpresa)
                .stream()
                .map(this::construirResponse)
                .toList();
    }

    @Override
    public UsuarioCompletoResponse actualizarEmpleado(
            Usuario usuario,
            Integer idEmpresa,
            Integer idRol) {

        EmpresaUsuario empresaUsuario = empresaUsuarioRepository
                .buscarPorUsuarioYEmpresa(usuario.getId(), idEmpresa)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No se encontró la relación empresa-usuario."
                ));

        if (idRol != null) {
            Rol nuevoRol = rolRepository.findById(idRol)
                    .orElseThrow(() -> new RecursoNoEncontradoException(
                            "El rol no existe."
                    ));
            empresaUsuario.setRol(nuevoRol);
        }

        EmpresaUsuario guardado = empresaUsuarioRepository.save(empresaUsuario);
        return construirResponse(guardado);
    }

    @Override
    public void eliminarEmpleado(
            Integer idEmpresa,
            Integer idUsuario) {

        empresaUsuarioRepository.deleteByUsuarioIdAndEmpresaId(
                idUsuario,
                idEmpresa
        );
    }

    private UsuarioCompletoResponse construirResponse(
            EmpresaUsuario empresaUsuario) {

        return empresaUsuarioMapper.toResponse(empresaUsuario);

    }


}