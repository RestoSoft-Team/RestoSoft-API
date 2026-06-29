package restosoftteam.restosoft.restosoftv2.mapper;

import org.springframework.stereotype.Component;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.LoginResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.RolResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Rol;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

@Component
public class AuthMapper {

    public UsuarioResponse toUsuarioResponse(Usuario usuario, Rol rol) {

        UsuarioResponse response = new UsuarioResponse();

        response.setId(usuario.getId());
        response.setNombres(usuario.getNombres());
        response.setApellidos(usuario.getApellidos());
        response.setEmail(usuario.getEmail());
        response.setTelefono(usuario.getTelefono());
        response.setFechaNacimiento(usuario.getFechaNacimiento());
        response.setEstado(usuario.getEstado());
        response.setImagenUrl(usuario.getImagenUrl());
        response.setRol(toRolResponse(rol));

        return response;

    }

    public EmpresaResponse toEmpresaResponse(Empresa empresa) {

        EmpresaResponse response = new EmpresaResponse();

        response.setId(empresa.getId());
        response.setNombre(empresa.getNombre());
        response.setDescripcion(empresa.getDescripcion());
        response.setDireccion(empresa.getDireccion());
        response.setTelefono(empresa.getTelefono());
        response.setEmail(empresa.getEmail());
        response.setEstado(empresa.getEstado());

        return response;

    }

    public RolResponse toRolResponse(Rol rol) {

        RolResponse response = new RolResponse();

        response.setId(rol.getId());
        response.setNombre(rol.getNombre());

        return response;

    }

    public LoginResponse toLoginResponse(String token,
                                         UsuarioResponse usuario,
                                         EmpresaResponse empresa,
                                         RolResponse rol) {

        return new LoginResponse(
                token,
                "Bearer",
                usuario,
                empresa,
                rol
        );

    }

}