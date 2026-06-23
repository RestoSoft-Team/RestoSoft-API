package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.UsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.service.UsuarioService;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@Valid @RequestBody UsuarioRequest request) {
        return new ResponseEntity<>(usuarioService.crearUsuario(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> obtenerUsuario(@PathVariable Integer id) {
        return ResponseEntity.ok(usuarioService.obtenerUsuarioPorId(id));
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioResponse>> obtenerClientes(){
        List<UsuarioResponse> lista = usuarioService.listarClientes();
        return ResponseEntity.ok(lista);
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponse>> listarPorEmpresa(@PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorEmpresa(idEmpresa));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> actualizarUsuario(
            @PathVariable Integer id, @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}