package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;

@Mapper(componentModel = "spring")
public interface ConfiguracionPaginaClienteMapper {
    ConfiguracionPaginaCliente toEntity(ConfiguracionPaginaClienteRequest request);
    ConfiguracionPaginaClienteResponse toResponse(ConfiguracionPaginaCliente entity);
    void updateEntityFromDto(ConfiguracionPaginaClienteRequest dto, @MappingTarget ConfiguracionPaginaCliente entity);



}
