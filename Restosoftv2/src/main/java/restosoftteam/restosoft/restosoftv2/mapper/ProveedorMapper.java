package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import restosoftteam.restosoft.restosoftv2.dto.request.ProveedorRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ProveedorResponse;
import restosoftteam.restosoft.restosoftv2.model.Proveedor;

@Mapper(componentModel = "spring")
public interface ProveedorMapper {

    Proveedor toEntity(ProveedorRequest request);

    ProveedorResponse toResponse(Proveedor proveedor);

    void updateEntityFromDto(ProveedorRequest dto, @MappingTarget Proveedor entity);
}
