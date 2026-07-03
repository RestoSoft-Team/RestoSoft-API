package restosoftteam.restosoft.restosoftv2.service.serviceimpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import restosoftteam.restosoft.restosoftv2.dto.request.CompraRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.DetalleCompraRequest;
import restosoftteam.restosoft.restosoftv2.mapper.CompraMapper;
import restosoftteam.restosoft.restosoftv2.model.Compra;
import restosoftteam.restosoft.restosoftv2.model.DetalleCompra;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.MovimientoInventario;
import restosoftteam.restosoft.restosoftv2.repository.CompraRepository;
import restosoftteam.restosoft.restosoftv2.repository.DetalleCompraRepository;
import restosoftteam.restosoft.restosoftv2.repository.InsumoRepository;
import restosoftteam.restosoft.restosoftv2.repository.MovimientoInventarioRepository;
import restosoftteam.restosoft.restosoftv2.service.CompraService;

@Service
@RequiredArgsConstructor
public class CompraServiceImpl implements CompraService {

    private final CompraRepository compraRepo;
    private final DetalleCompraRepository detalleRepo;
    private final InsumoRepository insumoRepo;
    private final MovimientoInventarioRepository movRepo;
    private final CompraMapper mapper;


    @Override
    @Transactional
    public void registrarCompra(CompraRequest request) {
        Compra compra = mapper.toEntity(request);
        compra = compraRepo.save(compra);

        for (DetalleCompraRequest detReq : request.getDetalles()){
            Insumo insumo = insumoRepo.findById(detReq.getIdInsumo()).orElseThrow();

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setInsumo(insumo);
            detalle.setCantidad(detReq.getCantidad());
            detalle.setPrecioUnitario(detReq.getPrecioUnitario());
            detalleRepo.save(detalle);

            insumo.setStockActual(insumo.getStockActual().add(detReq.getCantidad()));
            insumoRepo.save(insumo);

            MovimientoInventario mov = new MovimientoInventario();
            mov.setInsumo(insumo);
            mov.setTipoMovimiento("ENTRADA");
            mov.setCantidad(detReq.getCantidad());
            movRepo.save(mov);
        }
    }
}
