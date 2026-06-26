package restosoftteam.restosoft.restosoftv2.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import restosoftteam.restosoft.restosoftv2.security.resolver.IdEmpresaArgumentResolver;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IdEmpresaArgumentResolver idEmpresaArgumentResolver;

    public WebConfig(IdEmpresaArgumentResolver idEmpresaArgumentResolver) {
        this.idEmpresaArgumentResolver = idEmpresaArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(idEmpresaArgumentResolver);

    }

}