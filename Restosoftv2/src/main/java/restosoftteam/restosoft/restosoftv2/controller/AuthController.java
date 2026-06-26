package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.LoginRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.LoginResponse;
import restosoftteam.restosoft.restosoftv2.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/configuracion")
    public ResponseEntity<LoginResponse> loginConfiguracion(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.loginConfiguracion(request)
        );
    }

    @PostMapping("/cliente")
    public ResponseEntity<LoginResponse> loginCliente(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.loginCliente(request)
        );
    }

}