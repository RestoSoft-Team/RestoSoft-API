package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.Getter;
import lombok.Setter;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;

import java.math.BigDecimal;
@Getter
@Setter
public class PlatoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean representativo;
    private BigDecimal precio;
    private String imagenUrl;
    private Integer idcategoria;
    private String estado;
    private Integer idempresa;
}
