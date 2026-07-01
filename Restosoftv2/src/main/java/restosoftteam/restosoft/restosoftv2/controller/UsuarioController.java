package restosoftteam.restosoft.restosoftv2.controller;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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


    @PostMapping(value = "/empleados", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioCompletoResponse> crearEmpleado(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @Validated(EmpleadoRequest.OnCreate.class) @RequestPart("empleado") EmpleadoRequest request,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearEmpleado(idEmpresa, request, imagen));
    }


    @PostMapping(value = "/clientes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioResponse> crearCliente(
            @Parameter(hidden = true)
            @IdEmpresa Integer idEmpresa,
            @Validated(ClienteRequest.OnCreate.class) @RequestPart("cliente") ClienteRequest request,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearCliente(idEmpresa,
                        request,imagen));
    }

    @PostMapping(value = "/clientes/{idEmpresa}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioResponse> crearClienteLogin(
            @Parameter(hidden = true)
            @PathVariable Integer idEmpresa,
            @Validated(ClienteRequest.OnCreate.class) @RequestPart("cliente") ClienteRequest request,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(usuarioService.crearCliente(idEmpresa,
                        request,imagen));
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


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UsuarioCompletoResponse> actualizarEmpleado(
            @IdEmpresa Integer idEmpresa,
            @PathVariable Integer id,
            @Validated(EmpleadoRequest.OnUpdate.class) @RequestPart("empleado") EmpleadoRequest request,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen) {

        return ResponseEntity.ok(usuarioService.actualizarEmpleado(idEmpresa, id, request,imagen));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEmpleado(
            @IdEmpresa Integer idEmpresa,
            @PathVariable Integer id) {

        usuarioService.eliminarEmpleado(idEmpresa, id);
    }


    @GetMapping("/me")
    public ResponseEntity<UsuarioCompletoResponse> miPerfil(
            @IdUsuario Integer id,
            @IdEmpresa Integer idEmpresa) {

        return ResponseEntity.ok(usuarioService.obtenerEmpleado(idEmpresa, id));
    }

}