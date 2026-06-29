package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.EstiloInterfazEmpresaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.EstiloInterfazEmpresaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.EstiloInterfazEmpresaService;


@RestController
@RequestMapping("api/estilosEmpresa")
@RequiredArgsConstructor
public class EstiloInterfazEmpresaController {

    private final EstiloInterfazEmpresaService estiloInterfazEmpresaService;

    @GetMapping
    public ResponseEntity<EstiloInterfazEmpresaResponse> obtenerEstiloInterfazEmpresaPorId(@IdEmpresa Integer idEmpresa){
        EstiloInterfazEmpresaResponse  estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.findByIdEmpresa(idEmpresa);
        return ResponseEntity.ok(estiloInterfazEmpresaResponse);
    }

    @PostMapping
    public ResponseEntity<EstiloInterfazEmpresaResponse> guardarEstiloInterfazEmpresa(@Valid @RequestBody EstiloInterfazEmpresaRequest estiloInterfazEmpresaRequest,@IdEmpresa Integer idEmpresa){
        EstiloInterfazEmpresaResponse estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.save(estiloInterfazEmpresaRequest,idEmpresa);
        return new ResponseEntity<>(estiloInterfazEmpresaResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<EstiloInterfazEmpresaResponse> actualizarEstiloInterfazEmpresa(@Valid @RequestBody EstiloInterfazEmpresaRequest estiloInterfazEmpresaRequest, @IdEmpresa Integer id){
        EstiloInterfazEmpresaResponse estiloInterfazEmpresaResponse = estiloInterfazEmpresaService.update(estiloInterfazEmpresaRequest, id);
        return ResponseEntity.ok(estiloInterfazEmpresaResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminarEstiloInterfazEmpresa(@IdEmpresa Integer id){
        estiloInterfazEmpresaService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
