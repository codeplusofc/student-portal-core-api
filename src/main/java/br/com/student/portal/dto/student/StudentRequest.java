package br.com.student.portal.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentRequest {
    private String name;
    private String registration;
    private String course;

    public StudentRequest(String name, String registration, String course) {
        this.name = name;
        this.registration = registration;
        this.course = course;
    }
}
