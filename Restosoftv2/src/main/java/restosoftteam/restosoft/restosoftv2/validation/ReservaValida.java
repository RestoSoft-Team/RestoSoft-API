package restosoftteam.restosoft.restosoftv2.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import restosoftteam.restosoft.restosoftv2.validation.validator.ReservaValidaValidator;
import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ReservaValidaValidator.class)
public @interface ReservaValida {

    String message() default "La reserva no puede estar en el pasado";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}