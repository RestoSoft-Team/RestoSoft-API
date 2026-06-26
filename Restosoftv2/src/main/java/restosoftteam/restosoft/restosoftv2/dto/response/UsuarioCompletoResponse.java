package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioCompletoResponse {

    private UsuarioResponse usuario;

    private EmpresaResponse empresa;

    private RolResponse rol;

}