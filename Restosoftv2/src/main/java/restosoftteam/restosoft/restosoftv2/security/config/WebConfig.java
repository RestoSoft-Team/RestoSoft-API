package restosoftteam.restosoft.restosoftv2.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import restosoftteam.restosoft.restosoftv2.security.resolver.IdEmpresaArgumentResolver;
import restosoftteam.restosoft.restosoftv2.security.resolver.IdUsuarioArgumentResolver;
import restosoftteam.restosoft.restosoftv2.security.resolver.RolUsuarioArgumentResolver;
import restosoftteam.restosoft.restosoftv2.security.resolver.UsuarioActualArgumentResolver;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IdEmpresaArgumentResolver idEmpresaArgumentResolver;

    private final IdUsuarioArgumentResolver idUsuarioArgumentResolver;

    private final RolUsuarioArgumentResolver rolUsuarioArgumentResolver;

    private final UsuarioActualArgumentResolver usuarioActualArgumentResolver;

    public WebConfig(
            IdEmpresaArgumentResolver idEmpresaArgumentResolver,
            IdUsuarioArgumentResolver idUsuarioArgumentResolver,
            RolUsuarioArgumentResolver rolUsuarioArgumentResolver,
            UsuarioActualArgumentResolver usuarioActualArgumentResolver) {

        this.idEmpresaArgumentResolver = idEmpresaArgumentResolver;
        this.idUsuarioArgumentResolver = idUsuarioArgumentResolver;
        this.rolUsuarioArgumentResolver = rolUsuarioArgumentResolver;
        this.usuarioActualArgumentResolver = usuarioActualArgumentResolver;

    }

    @Override
    public void addArgumentResolvers(
            List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(idEmpresaArgumentResolver);
        resolvers.add(idUsuarioArgumentResolver);
        resolvers.add(rolUsuarioArgumentResolver);
        resolvers.add(usuarioActualArgumentResolver);

    }

}

