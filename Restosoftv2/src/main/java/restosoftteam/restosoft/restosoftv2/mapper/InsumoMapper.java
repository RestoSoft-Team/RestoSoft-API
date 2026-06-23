package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.InsumoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.InsumoResponse;
import restosoftteam.restosoft.restosoftv2.model.Insumo;

@Mapper(componentModel = "spring")
public interface InsumoMapper {

    @Mapping(source = "proveedor.id",     target = "idProveedor")
    @Mapping(source = "proveedor.nombre", target = "proveedorNombre")
    @Mapping(source = "empresa.id",       target = "idEmpresa")
    @Mapping(source = "empresa.nombre",   target = "empresaNombre")
    @Mapping(source = "categoria.nombre", target = "categoria")
    InsumoResponse toResponse(Insumo entity);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "empresa",   ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Insumo toEntity(InsumoRequest request);

    @Mapping(target = "id",        ignore = true)
    @Mapping(target = "empresa",   ignore = true)
    @Mapping(target = "proveedor", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    void updateEntityFromDto(InsumoRequest dto, @MappingTarget Insumo entity);

}