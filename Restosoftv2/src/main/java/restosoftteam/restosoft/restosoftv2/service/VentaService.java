package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.request.VentaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.VentaResponse;
import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    VentaResponse registrarVenta(VentaRequest request);
    List<VentaResponse> listarPorEmpresa(Integer idEmpresa);
    List<VentaResponse> listarPorFecha(LocalDate fecha, Integer idEmpresa);
    Double obtenerTotalDia(LocalDate fecha, Integer idEmpresa);
}