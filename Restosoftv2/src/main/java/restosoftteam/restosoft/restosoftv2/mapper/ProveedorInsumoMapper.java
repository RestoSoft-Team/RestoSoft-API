package restosoftteam.restosoft.restosoftv2.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorInsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorInsumoResponse;
import restosoftteam.restosoft.restosoftv2.model.ProveedorInsumo;

@Mapper(componentModel = "spring")
public interface ProveedorInsumoMapper {

    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "insumo", ignore = true)
    ProveedorInsumo toEntity(ProveedorInsumoRequest request);

    @Mapping(source = "proveedor.id",target = "idProveedor")
    @Mapping(source = "proveedor.nombre", target = "nombreProveedor")
    @Mapping(source = "insumo.id",target = "idInsumo")
    @Mapping(source = "insumo.nombre", target = "nombreInsumo")
    @Mapping(source = "proveedor.empresa.id", target = "idEmpresa")
    @Mapping(source = "proveedor.empresa.nombre", target = "nombreEmpresa")
    ProveedorInsumoResponse toResponse(ProveedorInsumo proveedorInsumo);

    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "insumo", ignore = true)
    void updateEntityFromDto(ProveedorInsumoRequest dto, @MappingTarget ProveedorInsumo entity);
}
