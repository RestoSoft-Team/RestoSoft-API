package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ReporteAsistenciaResponse {

    private Integer idUsuario;
    private String nombreCompleto;
    private Long totalEntradas;
    private Long totalSalidas;
    private Long asistenciasSinSalida;
}