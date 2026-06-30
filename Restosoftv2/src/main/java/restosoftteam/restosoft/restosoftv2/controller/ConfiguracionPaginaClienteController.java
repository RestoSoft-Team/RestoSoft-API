package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionPaginaClienteRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ConfiguracionPaginaClienteResponse;
import restosoftteam.restosoft.restosoftv2.service.ConfiguracionPaginaClienteService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/configuracion")
public class

ConfiguracionPaginaClienteController {
    private final ConfiguracionPaginaClienteService configuracionPaginaClienteService;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<ConfiguracionPaginaClienteResponse> obtenerConfiguracionPorIdEmpresa(@PathVariable Integer idEmpresa) {
        ConfiguracionPaginaClienteResponse configuracionResponse = configuracionPaginaClienteService.findByEmpresaId(idEmpresa);
        return ResponseEntity.ok(configuracionResponse);
    }

    @PostMapping
    public ResponseEntity<ConfiguracionPaginaClienteResponse> guardarConfiguracion(@Valid @RequestBody ConfiguracionPaginaClienteRequest configuracionPaginaClienteRequest) {
        ConfiguracionPaginaClienteResponse  configuracionResponse = configuracionPaginaClienteService.save(configuracionPaginaClienteRequest);
        return new ResponseEntity<>(configuracionResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionPaginaClienteResponse> actualizarConfiguracion(@Valid @RequestBody ConfiguracionPaginaClienteRequest request, @PathVariable Integer id) {
        ConfiguracionPaginaClienteResponse  configuracionResponse = configuracionPaginaClienteService.update(request,id);
        return ResponseEntity.ok(configuracionResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarConfiguracion(@PathVariable Integer id){
        configuracionPaginaClienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
