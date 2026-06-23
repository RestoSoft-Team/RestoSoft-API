package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.dto.response.ReporteAsistenciaResponse;
import restosoftteam.restosoft.restosoftv2.model.Asistencia;
import restosoftteam.restosoft.restosoftv2.repository.AsistenciaRepository;
import restosoftteam.restosoft.restosoftv2.service.ReporteService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final AsistenciaRepository asistenciaRepository;
    // Cuando Sebastián termine ventas agregas: private final VentaRepository ventaRepository;

    @Override
    public List<ReporteAsistenciaResponse> reporteAsistenciaEmpleados(
            Integer idEmpresa, LocalDate inicio, LocalDate fin) {

        Instant inicioInstant = inicio.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant finInstant = fin.atTime(23, 59, 59).toInstant(ZoneOffset.UTC);

        List<Asistencia> asistencias = asistenciaRepository
                .findByIdempresaIdAndFechaEntradaBetween(idEmpresa, inicioInstant, finInstant);

        Map<Integer, List<Asistencia>> porUsuario = asistencias.stream()
                .collect(Collectors.groupingBy(a -> a.getIdusuario().getId()));

        return porUsuario.entrySet().stream().map(entry -> {
            List<Asistencia> registros = entry.getValue();
            String nombre = registros.get(0).getIdusuario().getNombres()
                    + " " + registros.get(0).getIdusuario().getApellidos();
            long sinSalida = registros.stream()
                    .filter(a -> a.getFechaSalida() == null).count();

            return new ReporteAsistenciaResponse(
                    entry.getKey(), nombre,
                    (long) registros.size(),
                    registros.stream().filter(a -> a.getFechaSalida() != null).count(),
                    sinSalida
            );
        }).collect(Collectors.toList());
    }
}