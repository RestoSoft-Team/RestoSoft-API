package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.CierreCajaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.CierreCajaResponse;
import restosoftteam.restosoft.restosoftv2.exception.RecursoNoEncontradoException;
import restosoftteam.restosoft.restosoftv2.model.*;
import restosoftteam.restosoft.restosoftv2.repository.*;
import restosoftteam.restosoft.restosoftv2.service.CierreCajaService;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CierreCajaServiceImpl implements CierreCajaService {

    private final CierreCajaRepository repository;
    private final VentaRepository ventaRepository;
    private final CompraRepository compraRepository;
    private final DetallePedidoRepository detallePedidoRepository;
    private final PromocionRepository promocionRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    @Override
    @Transactional
    public CierreCajaResponse registrarOActualizarCierre(CierreCajaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada"));

        Optional<CierreCaja> existing = repository.findByEmpresa_IdAndFecha(request.getIdEmpresa(), request.getFecha());
        CierreCaja cierre;
        if (existing.isPresent()) {
            cierre = existing.get();
            cierre.setApertura(request.getApertura());
            cierre.setIngresosEfectivo(request.getIngresosEfectivo());
            cierre.setIngresosTarjeta(request.getIngresosTarjeta());
            cierre.setPropinas(request.getPropinas());
            cierre.setGastos(request.getGastos());
            cierre.setBalanceFinal(request.getBalanceFinal());
            cierre.setObservaciones(request.getObservaciones());
            cierre.setEstado(request.getEstado());
            cierre.setUsuario(usuario);
        } else {
            cierre = CierreCaja.builder()
                    .fecha(request.getFecha())
                    .apertura(request.getApertura())
                    .ingresosEfectivo(request.getIngresosEfectivo())
                    .ingresosTarjeta(request.getIngresosTarjeta())
                    .propinas(request.getPropinas())
                    .gastos(request.getGastos())
                    .balanceFinal(request.getBalanceFinal())
                    .observaciones(request.getObservaciones())
                    .estado(request.getEstado())
                    .usuario(usuario)
                    .empresa(empresa)
                    .build();
        }

        cierre = repository.save(cierre);
        return mapToResponse(cierre);
    }

    @Override
    public CierreCajaResponse obtenerCierrePorFecha(Integer idEmpresa, LocalDate fecha) {
        CierreCaja cierre = repository.findByEmpresa_IdAndFecha(idEmpresa, fecha)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cierre de caja no encontrado para la fecha especificada"));
        return mapToResponse(cierre);
    }

    @Override
    public List<CierreCajaResponse> listarPorEmpresa(Integer idEmpresa) {
        return repository.findByEmpresa_Id(idEmpresa).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CierreCajaResponse obtenerResumenDiario(Integer idEmpresa, LocalDate fecha) {
        Instant inicio = fecha.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fin = fecha.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant();

        // 1. Obtener Ventas del día
        List<Venta> ventas = ventaRepository.findByFechaBetween(inicio, fin).stream()
                .filter(v -> v.getIdempresa().getId().equals(idEmpresa))
                .collect(Collectors.toList());

        BigDecimal ingresosEfectivo = BigDecimal.ZERO;
        BigDecimal ingresosTarjeta = BigDecimal.ZERO;
        BigDecimal totalPropinas = BigDecimal.ZERO;

        // Obtener promociones vigentes para calcular el subtotal correcto de cada plato
        List<Promocion> promos = promocionRepository.findPromocionesVigentes();

        for (Venta v : ventas) {
            // Clasificar método de pago
            boolean isTarjeta = false;
            if (v.getMetodosPago() != null) {
                for (MetodoPago mp : v.getMetodosPago()) {
                    if (mp.getId() == 2 || mp.getNombre().toLowerCase().contains("tarjeta") ||
                            mp.getNombre().toLowerCase().contains("crédito") ||
                            mp.getNombre().toLowerCase().contains("débito")) {
                        isTarjeta = true;
                        break;
                    }
                }
            }

            if (isTarjeta) {
                ingresosTarjeta = ingresosTarjeta.add(v.getValorTotal());
            } else {
                ingresosEfectivo = ingresosEfectivo.add(v.getValorTotal());
            }

            // Calcular Propina = ValorTotal - (Subtotal con descuento + Impuesto)
            try {
                List<DetallePedido> detalles = detallePedidoRepository.findAllByPedido_Id(v.getIdpedido().getId());
                BigDecimal subtotalConPromo = BigDecimal.ZERO;
                for (DetallePedido dp : detalles) {
                    BigDecimal precioItem = dp.getPrecioUnitario();
                    // Buscar si hay promoción activa para este plato
                    for (Promocion promo : promos) {
                        if (promo.getPlatos().contains(dp.getPlato())) {
                            precioItem = precioItem.subtract(promo.getValorDescuento());
                            if (precioItem.compareTo(BigDecimal.ZERO) < 0) {
                                precioItem = BigDecimal.ZERO;
                            }
                            break;
                        }
                    }
                    BigDecimal cantidad = BigDecimal.valueOf(dp.getCantidad());
                    subtotalConPromo = subtotalConPromo.add(precioItem.multiply(cantidad));
                }

                BigDecimal impuesto = subtotalConPromo.multiply(BigDecimal.valueOf(0.19));
                BigDecimal propinaVenta = v.getValorTotal().subtract(subtotalConPromo.add(impuesto));

                if (propinaVenta.compareTo(BigDecimal.ZERO) > 0) {
                    totalPropinas = totalPropinas.add(propinaVenta);
                }
            } catch (Exception e) {
                // Si falla por alguna razón (por ejemplo, pedido sin detalles), asumir propina 0 para esta venta
                System.err.println("Error al calcular propina para venta ID: " + v.getId() + " - " + e.getMessage());
            }
        }

        // 2. Obtener Gastos (Compras del día)
        List<Compra> compras = compraRepository.findByFechaBetween(inicio, fin).stream()
                .filter(c -> c.getIdempresa().getId().equals(idEmpresa))
                .collect(Collectors.toList());

        BigDecimal totalGastos = compras.stream()
                .map(Compra::getTotalCompra)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 3. Obtener apertura si ya existe registro, o usar default de 200,000
        BigDecimal apertura = BigDecimal.valueOf(200000.00);
        String observaciones = "";
        String estado = "ABIERTA";
        Integer idCierre = null;

        Optional<CierreCaja> existing = repository.findByEmpresa_IdAndFecha(idEmpresa, fecha);
        if (existing.isPresent()) {
            apertura = existing.get().getApertura();
            observaciones = existing.get().getObservaciones();
            estado = existing.get().getEstado();
            idCierre = existing.get().getId();
        }

        BigDecimal balanceFinal = apertura.add(ingresosEfectivo).add(ingresosTarjeta).add(totalPropinas).subtract(totalGastos);

        return CierreCajaResponse.builder()
                .id(idCierre)
                .fecha(fecha)
                .apertura(apertura)
                .ingresosEfectivo(ingresosEfectivo)
                .ingresosTarjeta(ingresosTarjeta)
                .propinas(totalPropinas)
                .gastos(totalGastos)
                .balanceFinal(balanceFinal)
                .observaciones(observaciones)
                .estado(estado)
                .idUsuario(1) // Default Admin
                .nombreUsuario("Administrador")
                .idEmpresa(idEmpresa)
                .build();
    }

    private CierreCajaResponse mapToResponse(CierreCaja entity) {
        return CierreCajaResponse.builder()
                .id(entity.getId())
                .fecha(entity.getFecha())
                .apertura(entity.getApertura())
                .ingresosEfectivo(entity.getIngresosEfectivo())
                .ingresosTarjeta(entity.getIngresosTarjeta())
                .propinas(entity.getPropinas())
                .gastos(entity.getGastos())
                .balanceFinal(entity.getBalanceFinal())
                .observaciones(entity.getObservaciones())
                .estado(entity.getEstado())
                .idUsuario(entity.getUsuario().getId())
                .nombreUsuario(entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos())
                .idEmpresa(entity.getEmpresa().getId())
                .build();
    }
}
