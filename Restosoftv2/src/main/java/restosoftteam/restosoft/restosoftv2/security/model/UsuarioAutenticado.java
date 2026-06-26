package restosoftteam.restosoft.restosoftv2.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class UsuarioAutenticado {

    private final Integer usuarioId;

    private final Integer empresaId;

    private final String email;

    private final String rol;
}