package restosoftteam.restosoft.restosoftv2.mapper;

import org.mapstruct.Mapper;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.model.ConfiguracionPaginaCliente;

@Mapper(componentModel = "spring")
public interface ConfiguracionPaginaClienteMapper {
    ConfiguracionPaginaCliente toEntity(ConfiguracionPaginaClienteRequest configuracionPaginaClienteRequest);
    ConfiguracionPaginaClienteResponse toResponse(ConfiguracionPaginaCliente configuracionPaginaCliente);
}
