package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.model.Insumo;
import restosoftteam.restosoft.restosoftv2.model.ProveedorInsumo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InsumoMapper {

    @Mapping(source = "empresa.id",       target = "idEmpresa")
    @Mapping(source = "empresa.nombre",   target = "empresaNombre")
    @Mapping(source = "categoria.nombre", target = "categoria")
    @Mapping(source = "proveedores",      target = "idProveedor")
    @Mapping(source = "proveedores",      target = "proveedorNombre")
    InsumoResponse toResponse(Insumo entity);

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "empresa",     ignore = true)
    @Mapping(target = "proveedores", ignore = true)
    @Mapping(target = "categoria",   ignore = true)
    Insumo toEntity(InsumoRequest request);

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "empresa",     ignore = true)
    @Mapping(target = "proveedores", ignore = true)
    @Mapping(target = "categoria",   ignore = true)
    void updateEntityFromDto(InsumoRequest dto, @MappingTarget Insumo entity);


    default Integer mapearIdProveedor(List<ProveedorInsumo> proveedores) {
        if (proveedores != null && !proveedores.isEmpty()) {
            return proveedores.get(0).getProveedor().getId();
        }
        return null;
    }

    default String mapearNombreProveedor(List<ProveedorInsumo> proveedores) {
        if (proveedores != null && !proveedores.isEmpty()) {
            return proveedores.get(0).getProveedor().getNombre();
        }
        return "Sin proveedor";
    }
}