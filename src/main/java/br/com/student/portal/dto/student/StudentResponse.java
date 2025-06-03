package br.com.student.portal.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;
@AllArgsConstructor
@Data
public class StudentResponse {
    private UUID id;
    private String name;
    private String course;


}
