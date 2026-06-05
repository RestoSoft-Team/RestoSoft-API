package restosoftteam.restosoft.restosoftv2.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import restosoftteam.restosoft.restosoftv2.model.Empresa;
import restosoftteam.restosoft.restosoftv2.model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequest {
    @NotNull
    @FutureOrPresent
    private LocalDate fecha;

    private LocalTime hora;
    private Usuario idusuario;
    private Integer cantidadPersonas;
    private Empresa idempresa;
    private String estado;
}
