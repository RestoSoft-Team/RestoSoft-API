package restosoftteam.restosoft.restosoftv2.security.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer globalHeaderCustomizer() {
        return openApi -> openApi.getPaths().values().stream()
                .flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation -> {

                    operation.addParametersItem(new Parameter()
                            .in("header")
                            .name("idempresa")
                            .description("ID de la empresa (poner 1 para pruebas)")
                            .required(false)
                            .schema(new io.swagger.v3.oas.models.media.IntegerSchema())
                    );

                    operation.addParametersItem(new Parameter()
                            .in("header")
                            .name("Authorization")
                            .description("Token JWT (Incluir la palabra 'Bearer ' antes del token)")
                            .required(false)
                            .schema(new io.swagger.v3.oas.models.media.StringSchema())
                    );

                });
    }
}