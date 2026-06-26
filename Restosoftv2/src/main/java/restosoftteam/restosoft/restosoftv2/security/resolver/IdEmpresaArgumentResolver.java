package restosoftteam.restosoft.restosoftv2.security.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.security.model.UsuarioAutenticado;

@Component
public class IdEmpresaArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(IdEmpresa.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        String headerEmpresaId = webRequest.getHeader("idempresa");

        if (headerEmpresaId != null && !headerEmpresaId.isBlank()) {
            try {
                return Integer.parseInt(headerEmpresaId);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El header idempresa debe ser un número válido.");
            }
        }

        UsuarioAutenticado usuarioAutenticado = (UsuarioAutenticado) webRequest
                .getUserPrincipal();

        if (usuarioAutenticado != null && usuarioAutenticado.getEmpresaId() != null) {
            return usuarioAutenticado.getEmpresaId();
        }

        throw new RuntimeException(
                "Se requiere el ID de la empresa. Envíalo en el header 'idempresa' o inicia sesión con un token válido."
        );
    }
}