package restosoftteam.restosoft.restosoftv2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {

    private Integer id;
    private Instant fecha;
    private BigDecimal totalCompra;
    private String observaciones;

    private Proveedor proveedor;
    private Empresa empresa;
}