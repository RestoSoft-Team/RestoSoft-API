package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaUsuarioRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaUsuarioResponse;
import restosoftteam.restosoft.restosoftv2.service.EmpresaUsuarioService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/empresas-usuarios")
@RequiredArgsConstructor
public class EmpresaUsuarioController {

    private final EmpresaUsuarioService empresaUsuarioService;

    @PostMapping
    public ResponseEntity<EmpresaUsuarioResponse> asignar(
            @Valid @RequestBody EmpresaUsuarioRequest request) {
        return new ResponseEntity<>(empresaUsuarioService.asignar(request), HttpStatus.CREATED);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<EmpresaUsuarioResponse>> listarPorEmpresa(
            @PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(empresaUsuarioService.listarPorEmpresa(idEmpresa));
    }
}