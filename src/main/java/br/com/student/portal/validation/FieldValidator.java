package br.com.student.portal.validation;

import br.com.student.portal.exception.BadRequestException;
import org.springframework.stereotype.Component;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class FieldValidator {

    public static void validateRequiredField(Object fieldValue, String fieldName) {
        if (fieldValue == null || (fieldValue instanceof String && isEmpty((String) fieldValue))) {
            throw new BadRequestException(fieldName + " is a required field.");
        }
    }
}
