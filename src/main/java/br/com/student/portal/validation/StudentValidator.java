package br.com.student.portal.validation;

import br.com.student.portal.entity.StudentEntity;
import br.com.student.portal.entity.UserEntity;
import br.com.student.portal.exception.BadRequestException;
import org.springframework.stereotype.Component;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Component
public class StudentValidator {

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
    public static void validateCourse(String course){
        if (isEmpty(course)){
            validateRequiredField(course, "course");
        }
    }
    public static void validateFields(StudentEntity studentEntity) {
        validateName(studentEntity.getName());
        validateCourse(studentEntity.getCourse());

    }
}
