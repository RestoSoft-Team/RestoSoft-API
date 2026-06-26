package restosoftteam.restosoft.restosoftv2.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.RolResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.model.EmpresaUsuario;

@Component
@RequiredArgsConstructor
public class EmpresaUsuarioMapper {

    private final AuthMapper authMapper;

    public UsuarioCompletoResponse toResponse(EmpresaUsuario empresaUsuario) {

        UsuarioResponse usuario = authMapper.toUsuarioResponse(
                empresaUsuario.getUsuario(),
                empresaUsuario.getRol()
        );

        EmpresaResponse empresa = authMapper.toEmpresaResponse(
                empresaUsuario.getEmpresa()
        );

        RolResponse rol = authMapper.toRolResponse(
                empresaUsuario.getRol()
        );

        return new UsuarioCompletoResponse(
                usuario,
                empresa,
                rol
        );

    }

}