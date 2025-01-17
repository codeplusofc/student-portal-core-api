package br.com.student.portal.controller;


import br.com.student.portal.model.Student;
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
   public ResponseEntity<Student> createStudent(@RequestBody Student student){
        var newStudent = studentService.createStudent(student);
        return new ResponseEntity<>(newStudent, HttpStatus.CREATED);

   }

   @GetMapping
   public ResponseEntity<List<Student>> getAllStudent(){
        var students = studentService.getAllStudents();
        return new ResponseEntity<>(students, HttpStatus.OK);
   }

   @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable UUID id, @RequestBody Student studentDetails){
        var updateStudent = studentService.updateStudent(id, studentDetails);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteStudent (@PathVariable UUID id){
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }







}
