package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.MovimientoInventarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.MovimientoInventarioResponse;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.MovimientoInventario;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;
import restosoftteam.restosoft.restosoftv2.repository.EmpresaRepository;
import restosoftteam.restosoft.restosoftv2.repository.InsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.MovimientoInventarioRepository;
import restosoftteam.restosoft.restosoftv2.repository.ProveedorRepository;
import restosoftteam.restosoft.restosoftv2.mapper.MovimientoInventarioMapper;
import restosoftteam.restosoft.restosoftv2.service.MovimientoInventarioService;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovimientoInventarioServiceImpl implements MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final InsumoRepository insumoRepository;
    private final EmpresaRepository empresaRepository;
    private final ProveedorRepository proveedorRepository;
    private final MovimientoInventarioMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovimientoInventarioResponse> listar(Integer idEmpresa) {
        return movimientoRepository.findAllByEmpresaId(idEmpresa)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MovimientoInventarioResponse crear(MovimientoInventarioRequest request, Integer idEmpresa) {
        // 1. Mapeamos la request a la entidad
        MovimientoInventario movimiento = mapper.toEntity(request);

        // 2. Buscamos la empresa y la seteamos
        Empresa empresa = empresaRepository.findById(idEmpresa)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
        movimiento.setEmpresa(empresa);

        // 3. Buscamos el insumo y lo seteamos
        Insumo insumo = insumoRepository.findById(request.getIdInsumo())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado"));
        movimiento.setInsumo(insumo);

        // 4. Buscamos el proveedor y lo seteamos SOLO si viene en la request
        //    (las salidas/mermas NO requieren proveedor)
        if (request.getIdProveedor() != null) {
            Proveedor proveedor = proveedorRepository.findById(request.getIdProveedor())
                    .orElseThrow(() -> new RuntimeException("Proveedor no encontrado"));
            movimiento.setProveedor(proveedor);
        } else {
            movimiento.setProveedor(null);
        }

        BigDecimal stockActualBD = insumo.getStockActual() != null ? insumo.getStockActual() : BigDecimal.ZERO;
        BigDecimal cantidadMovimiento = request.getCantidad() != null ? request.getCantidad() : BigDecimal.ZERO;

        String tipoMov = (request.getTipoMovimiento() != null) ? request.getTipoMovimiento().toUpperCase() : "";
        BigDecimal nuevoStockCalculado;

        if (tipoMov.equals("ENTRADA")) {
            nuevoStockCalculado = stockActualBD.add(cantidadMovimiento);
        } else if (tipoMov.equals("SALIDA")
                || tipoMov.equals("MERMA")
                || tipoMov.equals("DANO")
                || tipoMov.equals("DAÑO")
                || tipoMov.equals("CONSUMO_INTERNO")) {
            nuevoStockCalculado = stockActualBD.subtract(cantidadMovimiento);
            if (nuevoStockCalculado.compareTo(BigDecimal.ZERO) < 0) {
                nuevoStockCalculado = BigDecimal.ZERO;
            }
        } else {
            nuevoStockCalculado = stockActualBD;
        }

        insumo.setStockActual(nuevoStockCalculado);
        insumoRepository.save(insumo);

        movimiento.setStockAnterior(stockActualBD);
        movimiento.setStockNuevo(nuevoStockCalculado);

        return mapper.toResponse(movimientoRepository.save(movimiento));
    }
}