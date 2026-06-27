package restosoftteam.restosoft.restosoftv2.controller;


import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.response.MesaResponse;
import restosoftteam.restosoft.restosoftv2.security.annotation.IdEmpresa;
import restosoftteam.restosoft.restosoftv2.service.MesaService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService mesaService;

    @PutMapping("/{idMesaA}/{idMesaB}")
    public ResponseEntity<MesaResponse> intercambioMesa (@PathVariable Integer idMesaA, @PathVariable Integer idMesaB) {
        mesaService.intercambiarUbicacionMesa(idMesaA, idMesaB);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<MesaResponse>> obtenerMesasPorEmpresa(@IdEmpresa Integer idEmpresa) {
        return ResponseEntity.ok(mesaService.obtenerMesasPorEmpresa(idEmpresa));
    }

}
