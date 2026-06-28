package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ConfiguracionesRestauranteRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.MesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.ZonaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ZonaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.MesaService;
import restosoftteam.restosoft.restosoftv2.service.ZonaService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/zonas")
@RequiredArgsConstructor
public class ZonaController {

    private final ZonaService zonaService;
    private final MesaService mesaService;

    @PostMapping
    public ResponseEntity<ZonaResponse> crearZona(@Valid @RequestBody ZonaRequest zonaRequest) {
        return new ResponseEntity<>(zonaService.crearZona(zonaRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ZonaResponse> actualizarZona(@PathVariable Integer id, @Valid @RequestBody ZonaRequest zonaRequest) {
        return ResponseEntity.ok(zonaService.actualizarZona(zonaRequest));
    }

    @DeleteMapping("/{id}/empresa/{idEmpresa}")
    public ResponseEntity<ZonaResponse> eliminarZona(@PathVariable Integer id, @PathVariable Integer idEmpresa) {
        return ResponseEntity.ok(zonaService.eliminarZona(id, idEmpresa));
    }

    @PostMapping("/guardarM")
    public ResponseEntity<Map<String, String>> guardarConfiguracionCompleta(@Valid @RequestBody ConfiguracionesRestauranteRequest request) {

        Map<Long, Integer> mapaIdsZonas = new HashMap<>();

        if (request.getZonas() != null) {
            for (ZonaRequest zonaRequest : request.getZonas()) {
                ZonaResponse zonaGuardada = zonaService.crearZona(zonaRequest);

                if (zonaRequest.getIdTemporal() != null) {
                    mapaIdsZonas.put(zonaRequest.getIdTemporal(), zonaGuardada.getId());
                }
            }
        }

        if (request.getMesas() != null) {
            for (MesaRequest mesaRequest : request.getMesas()) {

                Integer idZonaReal = mapaIdsZonas.get(mesaRequest.getIdZona());

                if (idZonaReal != null) {

                    mesaService.crearMesaConZonaReal(mesaRequest, idZonaReal);
                } else {
                    throw new RuntimeException("La mesa está asignada a una zona que no existe");
                }
            }
        }

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "El plano y las mesas se han guardado con éxito");
        response.put("status", "200");

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ZonaResponse>> obtenerZonasPorEmpresa(@IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(zonaService.obtenerZonasPorEmpresa(idEmpresa));
    }
}