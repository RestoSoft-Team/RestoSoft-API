package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.CierreCajaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.CierreCajaResponse;
import java.time.LocalDate;
import java.util.List;

public interface CierreCajaService {
    CierreCajaResponse registrarOActualizarCierre(CierreCajaRequest request);
    CierreCajaResponse obtenerCierrePorFecha(Integer idEmpresa, LocalDate fecha);
    List<CierreCajaResponse> listarPorEmpresa(Integer idEmpresa);
    CierreCajaResponse obtenerResumenDiario(Integer idEmpresa, LocalDate fecha);
}
