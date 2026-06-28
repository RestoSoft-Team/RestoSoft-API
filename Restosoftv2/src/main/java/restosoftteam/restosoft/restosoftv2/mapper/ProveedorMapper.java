package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

    @Mapping(target = "empresa", ignore = true) // Ignora la empresa aquí para setearla manualmente en el servicio
    Proveedor toEntity(ProveedorRequest request);

    @Mapping(source = "empresa.id", target ="idEmpresa")
    @Mapping(source = "empresa.nombre", target ="nombreEmpresa")
    ProveedorResponse toResponse(Proveedor proveedor);

    void updateEntityFromDto(ProveedorRequest dto, @MappingTarget Proveedor entity);
}
