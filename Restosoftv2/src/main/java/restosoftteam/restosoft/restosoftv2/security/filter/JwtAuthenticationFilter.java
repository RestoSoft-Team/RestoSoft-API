package restosoftteam.restosoft.restosoftv2.security.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import restosoftteam.restosoft.restosoftv2.security.jwt.JwtService;
import restosoftteam.restosoft.restosoftv2.security.model.UsuarioAutenticado;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;

        }

        String token = authorizationHeader.substring(7);

        jwtService.validarToken(token);

        Claims claims = jwtService.obtenerClaims(token);

        Integer usuarioId = claims.get("usuarioId", Integer.class);

        Integer empresaId = claims.get("empresaId", Integer.class);

        String rol = claims.get("rol", String.class);

        String email = claims.getSubject();

        UsuarioAutenticado usuarioAutenticado =
                new UsuarioAutenticado(

                        usuarioId,

                        empresaId,

                        email,

                        rol

                );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(

                        usuarioAutenticado,

                        null,

                        List.of(
                                new SimpleGrantedAuthority(
                                        "ROLE_" + rol
                                )
                        )

                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        filterChain.doFilter(request, response);

    }

}