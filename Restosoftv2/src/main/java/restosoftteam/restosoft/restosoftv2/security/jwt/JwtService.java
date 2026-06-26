package restosoftteam.restosoft.restosoftv2.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.security.exception.JwtAuthenticationException;
import restosoftteam.restosoft.restosoftv2.security.exception.TokenExpiradoException;
import restosoftteam.restosoft.restosoftv2.security.exception.TokenInvalidoException;
import restosoftteam.restosoft.restosoftv2.security.model.UsuarioAutenticado;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Genera un JWT.
     */
    public String generarToken(UsuarioAutenticado usuario) {

        Date ahora = new Date();

        Date expiracion = new Date(
                ahora.getTime() + expiration
        );

        return Jwts.builder()

                .setSubject(usuario.getEmail())

                .claim("usuarioId", usuario.getUsuarioId())

                .claim("empresaId", usuario.getEmpresaId())

                .claim("rol", usuario.getRol())

                .setIssuedAt(ahora)

                .setExpiration(expiracion)

                .signWith(obtenerClave(), SignatureAlgorithm.HS256)

                .compact();
    }

    /**
     * Extrae todos los Claims.
     */
    public Claims extraerClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(obtenerClave())

                .build()

                .parseClaimsJws(token)

                .getBody();
    }

    /**
     * Obtiene el email (Subject).
     */
    public String extraerEmail(String token) {
        return extraerClaims(token).getSubject();
    }

    /**
     * Obtiene el id del usuario.
     */
    public Integer extraerUsuarioId(String token) {
        return extraerClaims(token)
                .get("usuarioId", Integer.class);
    }

    /**
     * Obtiene el id de la empresa.
     */
    public Integer extraerEmpresaId(String token) {
        return extraerClaims(token)
                .get("empresaId", Integer.class);
    }

    /**
     * Obtiene el rol.
     */
    public String extraerRol(String token) {
        return extraerClaims(token)
                .get("rol", String.class);
    }

    /**
     * Indica si el token expiró.
     */
    public boolean tokenExpirado(String token) {

        return extraerClaims(token)

                .getExpiration()

                .before(new Date());
    }

    /**
     * Valida completamente el token.
     */
    public boolean validarToken(String token) {

        try {

            extraerClaims(token);

            return true;

        } catch (ExpiredJwtException e) {

            throw new TokenExpiradoException(
                    "El token ha expirado."
            );

        } catch (UnsupportedJwtException |
                 MalformedJwtException |
                 SecurityException |
                 IllegalArgumentException e) {

            throw new TokenInvalidoException(
                    "El token no es válido."
            );

        } catch (JwtException e) {

            throw new JwtAuthenticationException(
                    "Error al validar el JWT."
            );
        }
    }

    /**
     * Obtiene la clave de firma.
     */
    private SecretKey obtenerClave() {

        return Keys.hmacShaKeyFor(

                secret.getBytes(StandardCharsets.UTF_8)

        );
    }

}