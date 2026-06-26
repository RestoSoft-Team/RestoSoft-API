package restosoftteam.restosoft.restosoftv2.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import restosoftteam.restosoft.restosoftv2.security.exception.JwtAuthenticationException;
import restosoftteam.restosoft.restosoftv2.security.exception.TokenExpiradoException;
import restosoftteam.restosoft.restosoftv2.security.exception.TokenInvalidoException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generarToken(Integer usuarioId,
                               Integer empresaId,
                               String email,
                               String rol) {

        Date ahora = new Date();

        Date expiracion = new Date(
                ahora.getTime() + expiration
        );

        return Jwts.builder()

                .setSubject(email)

                .claim("usuarioId", usuarioId)

                .claim("empresaId", empresaId)

                .claim("rol", rol)

                .setIssuedAt(ahora)

                .setExpiration(expiracion)

                .signWith(obtenerClave(), SignatureAlgorithm.HS256)

                .compact();

    }


    public Claims obtenerClaims(String token) {

        return Jwts.parserBuilder()

                .setSigningKey(obtenerClave())

                .build()

                .parseClaimsJws(token)

                .getBody();

    }


    public void validarToken(String token) {

        try {

            obtenerClaims(token);

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


    private SecretKey obtenerClave() {

        return Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

    }

}