package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.EmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EmpresaResponse;
import restosoftteam.restosoft.restosoftv2.service.EmpresaService;

import java.util.List;

@RestController
@RequestMapping("/api/empresa")
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService empresaService;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponse> obtenerEmpresaPorId(@PathVariable int idEmpresa){
        EmpresaResponse empresaResponse = empresaService.findById(idEmpresa);
        return ResponseEntity.ok(empresaResponse);
    }

    @GetMapping()
    public ResponseEntity<List<EmpresaResponse>> listarEmpresas(){
        List<EmpresaResponse> empresaResponse = empresaService.findAll();
        return ResponseEntity.ok(empresaResponse);
    }

    @PostMapping()
    public ResponseEntity<EmpresaResponse> guardarEmpresa(@Valid @RequestBody EmpresaRequest empresaRequest){
        EmpresaResponse empresaResponse = empresaService.save(empresaRequest);
        return new ResponseEntity<>(empresaResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponse>  actualizarEmpresa(@PathVariable int idEmpresa, @Valid @RequestBody EmpresaRequest empresaRequest){
        EmpresaResponse empresaResponse = empresaService.update(empresaRequest, idEmpresa);
        return ResponseEntity.ok(empresaResponse);
    }

    @DeleteMapping("/{idEmpresa}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable int idEmpresa){
        empresaService.delete(idEmpresa);
        return ResponseEntity.noContent().build();
    }

}
