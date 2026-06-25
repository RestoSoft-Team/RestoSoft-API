package restosoftteam.restosoft.restosoftv2.security.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioAutenticado {

    private final Integer usuarioId;

    private final Integer empresaId;

    private final String email;

    private final String rol;

    private final Collection<? extends GrantedAuthority> authorities;

}