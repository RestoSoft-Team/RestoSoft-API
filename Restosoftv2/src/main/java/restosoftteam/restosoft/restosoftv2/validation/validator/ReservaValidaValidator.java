package restosoftteam.restosoft.restosoftv2.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import restosoftteam.restosoft.restosoftv2.dto.request.ReservaRequest;
import restosoftteam.restosoft.restosoftv2.validation.ReservaValida;

import java.time.LocalDateTime;

public class ReservaValidaValidator
        implements ConstraintValidator<ReservaValida, ReservaRequest> {

    @Override
    public boolean isValid(
            ReservaRequest reserva,
            ConstraintValidatorContext context) {

        if (reserva.getFecha() == null || reserva.getHora() == null) {
            return true;
        }

        LocalDateTime fechaHora = LocalDateTime.of(
                reserva.getFecha(),
                reserva.getHora()
        );

        return !fechaHora.isBefore(LocalDateTime.now());
    }
}