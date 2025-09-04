package br.com.student.portal.controller;


import br.com.student.portal.dto.student.StudentRequest;
import br.com.student.portal.dto.student.StudentResponse;
import br.com.student.portal.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
        return ResponseEntity.status(CREATED).body(studentService.createStudent(studentRequest));

    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudent() {
        return ResponseEntity.status(FOUND).body(studentService.getAllStudents());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@PathVariable UUID id, @RequestBody StudentRequest studentRequest) {
        return ResponseEntity.status(OK).body(studentService.updateStudent(id, studentRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
