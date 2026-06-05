package restosoftteam.restosoft.restosoftv2.dto.response;

import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.PlatoCategoria;

import java.math.BigDecimal;

public class PlatoResponse {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Boolean representativo;
    private BigDecimal precio;
    private String imagenUrl;
    private PlatoCategoria idcategoria;
    private String estado;
    private Empresa idempresa;
}
