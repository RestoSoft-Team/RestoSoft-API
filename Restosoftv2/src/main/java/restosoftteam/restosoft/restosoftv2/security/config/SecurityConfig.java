//package restosoftteam.restosoft.restosoftv2.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import restosoftteam.restosoft.restosoftv2.security.filter.JwtAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//
//    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
//        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//
//                // Deshabilita CSRF (JWT es Stateless)
//                .csrf(csrf -> csrf.disable())
//
//                // Habilita CORS
//                .cors(Customizer.withDefaults())
//
//                // No usar sesiones
//                .sessionManagement(session ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
//
//                // Configuración de permisos
//                .authorizeHttpRequests(auth -> auth
//
//                        // Endpoints públicos
//                        .requestMatchers(
//                                "/auth/login",
//                                "/auth/register"
//                        ).permitAll()
//
//                        // Permitir preflight de CORS
//                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//
//                        // Todo lo demás requiere autenticación
//                        .anyRequest().authenticated()
//
//                )
//
//                // Registrar el filtro JWT
//                .addFilterBefore(
//                        jwtAuthenticationFilter,
//                        UsernamePasswordAuthenticationFilter.class
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//
//        return new BCryptPasswordEncoder();
//
//    }
//
//}