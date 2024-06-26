package br.com.j4business.saga.fundacao.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

// Linking the AddressValidator class with @Address annotation.
@Constraint(validatedBy = { AtributoValidator.class })
// This constraint annotation can be used only on fields and method parameters.
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface Atributo {

    // The message to return when the instance of MyAddress fails the validation.
    String message() default "Atributo Fields must not be null/empty and obey character limit constraints";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
