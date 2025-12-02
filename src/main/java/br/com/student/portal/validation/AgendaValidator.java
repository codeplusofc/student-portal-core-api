package br.com.student.portal.validation;

import br.com.student.portal.exception.BadRequestException;
import org.springframework.stereotype.Component;

import static br.com.student.portal.validation.FieldValidator.validateRequiredField;
import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class AgendaValidator {

    public static void validateName(String name) {
        if (isEmpty(name)) {
            validateRequiredField(name, "Name");
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new BadRequestException("Name should only contain letters and spaces.");
        }
    }
}



