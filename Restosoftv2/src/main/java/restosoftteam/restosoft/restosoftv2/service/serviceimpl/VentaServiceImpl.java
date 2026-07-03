package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.VentaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.VentaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.mapper.VentaMapper;
import restosoftteam.restosoft.restosoftv2.model.*;
import restosoftteam.restosoft.restosoftv2.repository.*;
import restosoftteam.restosoft.restosoftv2.service.VentaService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VentaServiceImpl implements VentaService {
    private final VentaRepository ventaRepository;
    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final VentaMapper ventaMapper;

    @Override
    @Transactional
    public VentaResponse registrarVenta(VentaRequest request) {
        Pedido pedido = pedidoRepository.findById(request.getIdPedido())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido no encontrado"));
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));

        Venta venta;
        java.util.Optional<Venta> existingVenta = ventaRepository.findByIdpedido_Id(request.getIdPedido());

        if (existingVenta.isPresent()) {
            venta = existingVenta.get();
            venta.setValorTotal(java.math.BigDecimal.valueOf(request.getValorTotal()));
            venta.setObservaciones(request.getObservaciones());
            venta.setEstadoPago("PAGADO");
            venta.setIdusuario(usuario);
            venta.setFecha(request.getFecha() != null ? request.getFecha().atZone(java.time.ZoneId.systemDefault()).toInstant() : Instant.now());

            pedido.setEstado("PAGADO");
            pedidoRepository.save(pedido);
        } else {
            venta = ventaMapper.toEntity(request);
            venta.setIdpedido(pedido);
            venta.setIdusuario(usuario);
            venta.setIdempresa(empresa);
            venta.setFecha(request.getFecha() != null ? request.getFecha().atZone(java.time.ZoneId.systemDefault()).toInstant() : Instant.now());
            venta.setEstadoPago("PENDIENTE");

            pedido.setEstado("PENDIENTE");
            pedidoRepository.save(pedido);
        }

        // Asignar métodos de pago
        if (request.getIdMetodosPago() != null && !request.getIdMetodosPago().isEmpty()) {
            Set<MetodoPago> metodos = request.getIdMetodosPago().stream()
                    .map(id -> metodoPagoRepository.findById(id)
                            .orElseThrow(() -> new RecursoNoEncontradoException("Método de pago no encontrado")))
                    .collect(Collectors.toSet());
            venta.setMetodosPago(metodos);
        }

        venta = ventaRepository.save(venta);
        return ventaMapper.toResponse(venta);
    }

    @Override
    public List<VentaResponse> listarPorEmpresa(Integer idEmpresa) {
        return ventaRepository.findByIdempresa_Id(idEmpresa)
                .stream()
                .map(ventaMapper::toResponse) // ← usamos el mapper
                .collect(Collectors.toList());
    }

    @Override
    public List<VentaResponse> listarPorFecha(LocalDate fecha, Integer idEmpresa) {
        Instant inicio = fecha.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant();
        Instant fin = fecha.atTime(LocalTime.MAX).atZone(java.time.ZoneId.systemDefault()).toInstant();
        return ventaRepository.findByFechaBetween(inicio, fin).stream()
                .filter(v -> v.getIdempresa().getId().equals(idEmpresa))
                .map(ventaMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Double obtenerTotalDia(LocalDate fecha, Integer idEmpresa) {
        Instant inicio = fecha.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant();
        Instant fin = fecha.atTime(LocalTime.MAX).atZone(java.time.ZoneId.systemDefault()).toInstant();
        Double total = ventaRepository.sumTotalByDateRange(inicio, fin, idEmpresa);
        return total != null ? total : 0.0;
    }
}