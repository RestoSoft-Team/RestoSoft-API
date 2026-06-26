package restosoftteam.restosoft.restosoftv2.security.resolver;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import restosoftteam.restosoft.restosoftv2.security.annotation.RolUsuario;
import restosoftteam.restosoft.restosoftv2.security.exception.JwtAuthenticationException;
import restosoftteam.restosoft.restosoftv2.security.model.UsuarioAutenticado;

@Component
public class RolUsuarioArgumentResolver
        implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return parameter.hasParameterAnnotation(RolUsuario.class)
                && String.class.equals(parameter.getParameterType());

    }

    @Override
    public Object resolveArgument(

            MethodParameter parameter,

            ModelAndViewContainer mavContainer,

            NativeWebRequest webRequest,

            WebDataBinderFactory binderFactory) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication.getPrincipal()
                instanceof UsuarioAutenticado usuario)) {

            throw new JwtAuthenticationException(
                    "No existe un usuario autenticado."
            );

        }

        return usuario.getRol();

    }

}
