package restosoftteam.restosoft.restosoftv2.dto.response;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InsumoResponse {
    private Integer id;
    private String nombre;
    private String unidadMedida;
    private BigDecimal stockActual;
    private BigDecimal costoUnitario;
    private String estado;

    private Integer idProveedor;
    private String proveedorNombre;
    private Integer idEmpresa;
    private String empresaNombre;

    private String descripcion;
    private BigDecimal stockMinimo;
    private String categoria;
    private LocalDate fechaVencimiento;


}
