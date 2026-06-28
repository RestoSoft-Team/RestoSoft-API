package restosoftteam.restosoft.restosoftv2.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import restosoftteam.restosoft.restosoftv2.security.filter.JwtAuthenticationFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // 1. CONFIGURACIÓN CORS GLOBAL (Para que VS Code Live Server funcione)
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permitir el origen de VS Code Live Server
        configuration.setAllowedOrigins(List.of("http://127.0.0.1:5500", "http://localhost:5500"));

        // Permitir todos los métodos (GET, POST, PUT, DELETE, OPTIONS)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // Permitir todos los headers (incluidos idempresa y Authorization)
        configuration.setAllowedHeaders(List.of("*"));

        // Permitir el envío de credenciales (tokens)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Aplicar a toda la API
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        System.out.println("!!!!!!!!!!!!! MI SECURITY CONFIG SE ESTÁ EJECUTANDO !!!!!!!!!!!!!");

        http
                .csrf(csrf -> csrf.disable())
                // 2. USAR LA CONFIGURACIÓN CORS QUE ACABAMOS DE CREAR
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth

                        // 3. AUTH PÚBLICO
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/configuracion",
                                "/api/auth/cliente",
                                "/api/auth/register"
                        ).permitAll()

                        // 4. SWAGGER PÚBLICO
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // 5. CREACIÓN PÚBLICA (POST)
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/empleados").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/usuarios/clientes").permitAll()

                        // 6. LISTAR CLIENTES PÚBLICO (GET) -> AGREGADO PARA ARREGLAR TU 403
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/clientes").permitAll()

                        // 7. PREFLIGHT DE CORS
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // 8. TODO LO DEMÁS REQUIERE AUTENTICACIÓN
                        .anyRequest().authenticated()

                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}