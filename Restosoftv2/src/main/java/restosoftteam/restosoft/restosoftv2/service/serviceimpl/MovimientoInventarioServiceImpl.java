package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.MovimientoInventario;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.InsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.MovimientoInventarioRepository;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository repository;
    private final InsumoRepository insumoRepository;
    private final EmpresaRepository empresaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponse> listar() {
        return repository.findAll().stream().map(m -> {
            String nombreInsumo = m.getInsumo() != null ? m.getInsumo().getNombre() : "Sin insumo";

            String nombreProveedor = "Sin proveedor";
            if (m.getInsumo() != null && m.getInsumo().getProveedores() != null && !m.getInsumo().getProveedores().isEmpty()) {
                nombreProveedor = m.getInsumo().getProveedores().get(0).getProveedor().getNombre();
            }

            BigDecimal costoUnitario = m.getInsumo() != null ? m.getInsumo().getCostoUnitario() : null;

            return new MovimientoInventarioResponse(
                    m.getId(),
                    m.getTipoMovimiento(),
                    m.getCantidad(),
                    m.getStockAnterior(),
                    m.getStockNuevo(),
                    nombreInsumo,
                    nombreProveedor,
                    costoUnitario,
                    m.getFechaMovimiento(),
                    m.getObservacion()
            );
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MovimientoInventarioResponse crear(MovimientoInventarioRequest request) {
        Insumo insumo = insumoRepository.findById(request.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));

        Empresa empresa = empresaRepository.findById(request.getIdEmpresa())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        BigDecimal stockAnterior = insumo.getStockActual() != null
                ? insumo.getStockActual() : BigDecimal.ZERO;

        BigDecimal stockNuevo;

        if ("ENTRADA".equalsIgnoreCase(request.getTipoMovimiento())) {
            stockNuevo = stockAnterior.add(request.getCantidad());
        } else {
            stockNuevo = stockAnterior.subtract(request.getCantidad());
        }

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInsumo(insumo);
        movimiento.setEmpresa(empresa);
        movimiento.setTipoMovimiento(request.getTipoMovimiento());
        movimiento.setCantidad(request.getCantidad());
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockNuevo(stockNuevo);
        movimiento.setObservacion(request.getObservacion());
        movimiento.setFechaMovimiento(request.getFechaMovimiento() != null
                ? request.getFechaMovimiento() : Instant.now());

        insumo.setStockActual(stockNuevo);
        insumoRepository.save(insumo);

        MovimientoInventario guardado = repository.save(movimiento);

        String nombreProveedor = "Sin proveedor";
        if (insumo.getProveedores() != null && !insumo.getProveedores().isEmpty()) {
            nombreProveedor = insumo.getProveedores().get(0).getProveedor().getNombre();
        }

        return new MovimientoInventarioResponse(
                guardado.getId(),
                guardado.getTipoMovimiento(),
                guardado.getCantidad(),
                guardado.getStockAnterior(),
                guardado.getStockNuevo(),
                insumo.getNombre(),
                nombreProveedor,
                insumo.getCostoUnitario(),
                guardado.getFechaMovimiento(),
                guardado.getObservacion()
        );
    }
}