package br.com.student.portal.validation;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class TaskValidator {
    public void validateName(String name){
        if(name.isEmpty()){
            throw new RuntimeException("Name can't be empty");
        }

    }
    public void validateDeadLine(LocalDateTime deadline){
        if(deadline == null){
            throw new RuntimeException("Date can't be null");
        }
        if(deadline.isBefore(LocalDateTime.now())){
            throw new RuntimeException("Date can't be in the paste");
        }
    }
    public void validateDescription(String description){
        if (description.isEmpty()){
            throw new RuntimeException("Description can't be empty");
        }
    }
    public void validateTitle(String title){
        if(title.isEmpty()){
            throw new RuntimeException("Title can't be empty");
        }
    }
    public void validateCourse(String course){
        if (course.isEmpty()){
            throw new RuntimeException("Course can't be empty");
        }
    }
}
