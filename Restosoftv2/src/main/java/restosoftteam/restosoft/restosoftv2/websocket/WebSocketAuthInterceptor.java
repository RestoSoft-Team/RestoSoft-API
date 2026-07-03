package restosoftteam.restosoft.restosoftv2.websocket;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    @Value("${jwt.secret}") 
    private String jwtSecret;

    private static final List<String> ALLOWED_ROLES = List.of("ADMINISTRADOR", "RECEPCIONISTA", "MESERO", "COCINA", "CONFIGURACION");

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new IllegalArgumentException("Falta el token de autorización en la conexión WebSocket");
            }

            String token = authHeader.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String role = claims.get("rol", String.class);

                if (role == null || !ALLOWED_ROLES.contains(role.toUpperCase())) {
                    throw new IllegalArgumentException("Acceso denegado: El rol CLIENTE o usuarios sin permisos no pueden conectarse al WebSocket");
                }

            } catch (Exception e) {
                throw new IllegalArgumentException("Token JWT inválido, expirado o sin permisos: " + e.getMessage());
            }
        }

        return message;
    }
}