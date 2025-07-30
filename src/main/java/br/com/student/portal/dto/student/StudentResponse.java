package br.com.student.portal.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentResponse {
    private UUID id;
    private String name;
    private String course;
    private String registration;


}
