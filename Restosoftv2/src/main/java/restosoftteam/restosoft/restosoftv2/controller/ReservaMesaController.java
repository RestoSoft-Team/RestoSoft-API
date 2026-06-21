package restosoftteam.restosoft.restosoftv2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponseMapStruct;
import restosoftteam.restosoft.restosoftv2.service.ReservaMesaService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reserva-mesa")
@RequiredArgsConstructor
public class ReservaMesaController {
    private final ReservaMesaService reservaMesaService;

    @GetMapping("/reserva/{idReserva}")
    public ResponseEntity<List<ReservaMesaResponseMapStruct>> listarPorReservaId(@PathVariable Integer idReserva) {
        List<ReservaMesaResponseMapStruct> lista = reservaMesaService.findAllByReservaId(idReserva);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empresa/{idEmpresa}/hoy")
    public ResponseEntity<List<ReservaMesaResponseMapStruct>> listarPorIdEmpresaDiario(@PathVariable Integer idEmpresa) {
        List<ReservaMesaResponseMapStruct> lista = reservaMesaService.findAllByEmpresaIdDiario(idEmpresa);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<ReservaMesaResponseMapStruct>> listarPorIdEmpresaGeneral(@PathVariable Integer idEmpresa) {
        List<ReservaMesaResponseMapStruct> lista = reservaMesaService.findAllByEmpresaIdGeneral(idEmpresa);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/mesa/{idMesa}")
    public ResponseEntity<List<ReservaMesaResponseMapStruct>> listarPorMesaId(@PathVariable Integer idMesa) {
        List<ReservaMesaResponseMapStruct> lista = reservaMesaService.findAllByMesaId(idMesa);
        return ResponseEntity.ok(lista);
    }

    @DeleteMapping("/reserva/{idReserva}/mesa/{idMesa}")
    public ResponseEntity<Void> eliminarPorIdReservaYIdMesa(
            @PathVariable Integer idReserva,
            @PathVariable Integer idMesa) {

        reservaMesaService.deleteByReservaAndMesa(idReserva, idMesa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reserva/{idReserva}")
    public ResponseEntity<Void> eliminarPorIdReserva(@PathVariable Integer idReserva) {
        reservaMesaService.deleteAllByReserva(idReserva);
        return ResponseEntity.noContent().build();
    }

}
