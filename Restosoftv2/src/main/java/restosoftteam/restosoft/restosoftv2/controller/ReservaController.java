package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaCompletaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaMesaRequest;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponseMapStruct;
import restosoftteam.restosoft.restosoftv2.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaMesaResponse> guardarReserva(
            @Valid @RequestBody ReservaCompletaRequest request) {

        ReservaMesaResponse reservaMesaResponse = reservaService.save(request.getReserva(), request.getIdsMesa());
        return new ResponseEntity<>(reservaMesaResponse, HttpStatus.CREATED);

    }

    @PutMapping("/{idReserva}")
    public ResponseEntity<ReservaMesaResponse> actualizarReserva(@PathVariable Integer idReserva, @Valid @RequestBody ReservaCompletaRequest request) {

        System.out.println(idReserva);
        System.out.println(request);

        ReservaMesaResponse reservaMesaResponse = reservaService.update(request.getReserva(), idReserva, request.getIdsMesa());
        return ResponseEntity.ok(reservaMesaResponse);
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Integer idReserva) {
        reservaService.delete(idReserva);
        return ResponseEntity.noContent().build();
    }

}
