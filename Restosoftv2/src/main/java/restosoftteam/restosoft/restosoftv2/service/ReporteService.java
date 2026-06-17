package restosoftteam.restosoft.restosoftv2.service;

import restosoftteam.restosoft.restosoftv2.dto.response.ReporteAsistenciaResponse;
import java.time.LocalDate;
import java.util.List;

public interface ReporteService {

    List<ReporteAsistenciaResponse> reporteAsistenciaEmpleados(
            Integer idEmpresa, LocalDate inicio, LocalDate fin);
}