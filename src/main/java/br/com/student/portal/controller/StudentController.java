package br.com.student.portal.controller;


import br.com.student.portal.entity.StudentEntity;
import br.com.student.portal.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(@RequestBody StudentEntity studentEntity) {
        var student = studentService.createStudent(studentEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);

    }

    @GetMapping
    public ResponseEntity<List<StudentEntity>> getAllStudent() {
        var students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable UUID id, @RequestBody StudentEntity studentEntity) {
        var updatedStudent = studentService.updateStudent(id, studentEntity);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable UUID id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

}
