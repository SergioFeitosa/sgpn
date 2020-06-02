package br.com.j4business.saga.fundacao.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.j4business.saga.evento.model.EventoForm;

public class AtributoValidator implements ConstraintValidator<Atributo, EventoForm> {

    public void initialize(Atributo constraintAnnotation) {
    }

    /**
     * 1. A null address is handled by the @NotNull constraint on the @Address.
     * 2. The address should have all the data values specified.
     * 3. Pin code in the address should be of at least 6 characters.
     * 4. The country in the address should be of at least 4 characters.
     */
    public boolean isValid(EventoForm value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

    	// Validação para campo nulo
    	if (value.getEventoNome() == null) {
        return false;
        }

    	// Validação para campo vazio
        if (value.getEventoNome().isEmpty()) {
            return false;
        }

        return true;
    }

}