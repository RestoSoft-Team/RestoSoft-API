package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.service.EstiloInterfazEmpresaService;


@RestController
@RequestMapping("api/estilosEmpresa")
@RequiredArgsConstructor
public class EstiloInterfazEmpresaController {

    private final EstiloInterfazEmpresaService estiloInterfazEmpresaService;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<EstiloInterfazEmpresaResponse> obtenerEstiloInterfazEmpresaPorId(@PathVariable Integer idEmpresa){
        EstiloInterfazEmpresaResponse  estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.findByIdEmpresa(idEmpresa);
        return ResponseEntity.ok(estiloInterfazEmpresaResponse);
    }

    @PostMapping
    public ResponseEntity<EstiloInterfazEmpresaResponse> guardarEstiloInterfazEmpresa(@Valid @RequestBody EstiloInterfazEmpresaRequest estiloInterfazEmpresaRequest){
        EstiloInterfazEmpresaResponse estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.save(estiloInterfazEmpresaRequest);
        return new ResponseEntity<>(estiloInterfazEmpresaResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstiloInterfazEmpresaResponse> actualizarEstiloInterfazEmpresa(@Valid @RequestBody EstiloInterfazEmpresaRequest estiloInterfazEmpresaRequest, @PathVariable Integer id){
        EstiloInterfazEmpresaResponse estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.update(estiloInterfazEmpresaRequest, id);
        return ResponseEntity.ok(estiloInterfazEmpresaResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstiloInterfazEmpresa(@PathVariable Integer id){
        estiloInterfazEmpresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
