package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.VentaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.VentaResponse;
import restosoftteam.restosoft.restosoftv2.model.Venta;

@Mapper(componentModel = "spring")
public interface VentaMapper {

    // Convertir VentaRequest → Venta (ignoramos relaciones que se asignan manualmente)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idpedido", ignore = true)
    @Mapping(target = "idusuario", ignore = true)
    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "metodosPago", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    Venta toEntity(VentaRequest request);

    // Convertir Venta → VentaResponse
    @Mapping(source = "id", target = "idVenta")
    @Mapping(target = "nombreUsuario", expression = "java(venta.getIdusuario() != null ? (venta.getIdusuario().getNombres() != null ? venta.getIdusuario().getNombres() : \"\") + \" \" + (venta.getIdusuario().getApellidos() != null ? venta.getIdusuario().getApellidos() : \"\") : null)")
    @Mapping(target = "fecha", expression = "java(venta.getFecha() != null ? java.time.LocalDateTime.ofInstant(venta.getFecha(), java.time.ZoneId.systemDefault()) : null)")
    @Mapping(target = "metodosPago", expression = "java(venta.getMetodosPago() != null ? venta.getMetodosPago().stream().map(mp -> mp.getNombre()).collect(java.util.stream.Collectors.toSet()) : null)")
    @Mapping(target = "idPedido", expression = "java(venta.getIdpedido() != null ? venta.getIdpedido().getId() : null)")
    VentaResponse toResponse(Venta venta);

    // Actualizar una entidad existente desde un DTO (para updates)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idpedido", ignore = true)
    @Mapping(target = "idusuario", ignore = true)
    @Mapping(target = "idempresa", ignore = true)
    @Mapping(target = "metodosPago", ignore = true)
    @Mapping(target = "fecha", ignore = true)
    void updateEntity(VentaRequest request, @MappingTarget Venta venta);
}