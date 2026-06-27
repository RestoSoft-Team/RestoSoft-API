package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpleadoRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioCompletoResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.UsuarioResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdUsuario;
import restosoftteam.restosoft.restosoftv2.service.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping("/empleados")
    public ResponseEntity<UsuarioCompletoResponse> crearEmpleado(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @Valid @RequestBody EmpleadoRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearEmpleado(idEmpresa, request));
    }


    @PostMapping("/clientes")
    public ResponseEntity<UsuarioResponse> crearCliente(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @Valid @RequestBody ClienteRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearCliente(request));
    }


    @GetMapping
    public ResponseEntity<List<UsuarioCompletoResponse>> listarEmpleados(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {

        return ResponseEntity.ok(usuarioService.listarEmpleados(idEmpresa));
    }

    @GetMapping("/clientes")
    public ResponseEntity<List<UsuarioCompletoResponse>> listarClientes(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa) {

        System.out.println(idEmpresa + "Id empresa");

        return ResponseEntity.ok(usuarioService.listarClientes(idEmpresa));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCompletoResponse> obtenerEmpleado(
            @IdEmpresa Integer idEmpresa,
            @PathVariable Integer id) {

        return ResponseEntity.ok(usuarioService.obtenerEmpleado(idEmpresa, id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<UsuarioCompletoResponse> actualizarEmpleado(
            @IdEmpresa Integer idEmpresa,
            @PathVariable Integer id,
            @Valid @RequestBody EmpleadoRequest request) {

        return ResponseEntity.ok(usuarioService.actualizarEmpleado(idEmpresa, id, request));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEmpleado(
            @IdEmpresa Integer idEmpresa,
            @PathVariable Integer id) {

        usuarioService.eliminarEmpleado(idEmpresa, id);
    }


    @GetMapping("/me")
    public ResponseEntity<UsuarioResponse> miPerfil(
            @IdUsuario Integer idUsuario) {

        return ResponseEntity.ok(usuarioService.obtenerUsuario(idUsuario));
    }

}