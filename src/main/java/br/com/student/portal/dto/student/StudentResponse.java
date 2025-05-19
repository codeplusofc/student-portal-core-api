package br.com.student.portal.dto.student;

import jakarta.persistence.Id;
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
