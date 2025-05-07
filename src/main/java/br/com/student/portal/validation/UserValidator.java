package br.com.student.portal.validation;

import br.com.student.portal.dto.UserRequest;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import org.springframework.stereotype.Component;

import static io.micrometer.common.util.StringUtils.isEmpty;
import static java.util.regex.Pattern.matches;

@Component
public class UserValidator {


    //TODO: adding regex on the one Utils

    public static void validateRequiredField(Object fieldValue, String fieldName) {
        if (fieldValue == null || (fieldValue instanceof String && isEmpty((String) fieldValue))) {
            throw new BadRequestException(fieldName + " is a required field.");
        }
    }

    public static void validateName(String name) {
        if (isEmpty(name)) {
            validateRequiredField(name, "Name");
        }

        if (!name.matches("^[a-zA-Z\\s]+$")) {
            throw new BadRequestException("Name should only contain letters and spaces.");
        }
    }

    public static void validateRegistration(String registration) {
        if (isEmpty(registration)) {
            validateRequiredField(registration, "Name");
        }
    }

    public static void validateEmail(String email) {
        if (isEmpty(email)) {
            validateRequiredField(email, "Email");
        }

        var emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!matches(emailRegex, email)) {
            throw new BadRequestException("Invalid email format.");
        }

        if (!email.endsWith("@gmail.com")) {
            throw new BadRequestException("Unrecognized Gmail Account Access Attempt");
        }
    }

    public static void validatePassword(String password) {
        if (isEmpty(password)) {
            validateRequiredField(password, "Password");
        }

        var passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        if (!matches(passwordRegex, password)) {
            throw new BadRequestException("Password must be at least 8 characters long, contain at least one letter, one number, and one special character.");
        }
    }

    public static void validateFields(UserEntity userEntity) {
        validateName(userEntity.getName());
        validateEmail(userEntity.getEmail());
        validatePassword(userEntity.getPassword());
        validateRegistration(userEntity.getRegistration());
    }
    public static void validateFieldsUserRequest(UserRequest userRequest) {
        validateName(userRequest.getName());
        validateEmail(userRequest.getEmail());
        validatePassword(userRequest.getPassword());
    }

}
