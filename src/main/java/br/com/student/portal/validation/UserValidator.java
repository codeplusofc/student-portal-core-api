package br.com.student.portal.validation;

import br.com.student.portal.exception.BadRequestException;
import br.com.student.portal.model.User;

import org.springframework.stereotype.Component;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class UserValidator {

    public static void validateRequiredField(Object fieldValue, String fieldName) {
        if (fieldValue == null || (fieldValue instanceof String && isEmpty((String) fieldValue))) {
            throw new BadRequestException(fieldName + " is a required field.");
        }
    }

    public static void validateName(String name) {
        if (isEmpty(name)) {
            validateRequiredField(name, "Name");
        }
    }

    public static void validateEmail(String email) {
        if (isEmpty(email)) {
            validateRequiredField(email, "Email");
        }

        if (!email.endsWith("@gmail.com")) {
            throw new BadRequestException("Unrecognized Gmail Account Access Attempt");
        }

    }

    public static void validatePassword(String password) {
        if (isEmpty(password)) {
            validateRequiredField(password, "Password");
        }
    }
    public static void validateFields(User user){
        validateName(user.getName());
        validateEmail(user.getEmail());
        validatePassword(user.getPassword());

    }

}
