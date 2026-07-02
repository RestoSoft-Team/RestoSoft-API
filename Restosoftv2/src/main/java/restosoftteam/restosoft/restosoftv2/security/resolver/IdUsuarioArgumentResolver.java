package restosoftteam.restosoft.restosoftv2.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import restosoftteam.restosoft.restosoftv2.security.annotation.IdUsuario;
import restosoftteam.restosoft.restosoftv2.security.exception.JwtAuthenticationException;
import restosoftteam.restosoft.restosoftv2.security.model.UsuarioAutenticado;

@Component
public class IdUsuarioArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(IdUsuario.class)
                && Integer.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        try {
            String headerUsuarioId = webRequest.getHeader("idusuario");

            if (headerUsuarioId != null && !headerUsuarioId.isBlank()) {
                try {
                    return Integer.parseInt(headerUsuarioId);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(
                            "El header 'idusuario' debe ser un número válido."
                    );
                }
            }

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            System.out.println(">>> DEBUG AUTH: " + authentication);
            System.out.println(">>> DEBUG PRINCIPAL: "
                    + (authentication != null ? authentication.getPrincipal() : "NULL"));

            if (!(authentication.getPrincipal() instanceof UsuarioAutenticado usuario)) {
                throw new JwtAuthenticationException(
                        "No existe un usuario autenticado."
                );
            }

            if (usuario.getUsuarioId() == null) {
                throw new JwtAuthenticationException(
                        "El usuario autenticado no tiene un ID de usuario asociado en el token."
                );
            }

            return usuario.getUsuarioId();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}