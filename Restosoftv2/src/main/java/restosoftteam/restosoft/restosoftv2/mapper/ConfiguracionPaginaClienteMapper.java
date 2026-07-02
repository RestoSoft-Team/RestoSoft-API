package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;

@Mapper(componentModel = "spring")
public interface ConfiguracionPaginaClienteMapper {

    @Mapping(target = "empresa", ignore = true)
    ConfiguracionPaginaCliente toEntity(ConfiguracionPaginaClienteRequest request);

    @Mapping(source = "empresa.id", target = "idEmpresa")
    ConfiguracionPaginaClienteResponse toResponse(ConfiguracionPaginaCliente entity);

    @Mapping(target = "empresa", ignore = true)
    void updateEntityFromDto(ConfiguracionPaginaClienteRequest dto, @MappingTarget ConfiguracionPaginaCliente entity);


}
