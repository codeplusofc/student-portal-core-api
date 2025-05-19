package br.com.student.portal.dto.student;

import lombok.Data;

@Data
public class StudentRequest {
    private String name;
    private String registration;
    private String course;
}
