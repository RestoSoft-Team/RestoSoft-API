package restosoftteam.restosoft.restosoftv2.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.dto.response.ReservaMesaResponse;
import restosoftteam.restosoft.restosoftv2.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
@RequiredArgsConstructor
public class ReservaController {
    private final ReservaService reservaService;

    @PostMapping
    public ResponseEntity<ReservaMesaResponse> guardarReserva(@Valid @RequestBody ReservaRequest reservaRequest, @Valid @RequestBody List<Integer> idsMesa){
        ReservaMesaResponse reservaMesaResponse = reservaService.save(reservaRequest,idsMesa);
        return new ResponseEntity<>(reservaMesaResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{idReserva}")
    public ResponseEntity<ReservaMesaResponse> actualizarReserva(@PathVariable Integer idReserva,@Valid @RequestBody ReservaRequest reservaRequest, @Valid @RequestBody List<Integer> idsMesa){
        ReservaMesaResponse reservaMesaResponse = reservaService.update(reservaRequest,idReserva,idsMesa);
        return ResponseEntity.ok(reservaMesaResponse);
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Integer idReserva){
        reservaService.delete(idReserva);
        return ResponseEntity.noContent().build();
    }

}
