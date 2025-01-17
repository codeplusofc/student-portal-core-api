package br.com.student.portal.service;


import br.com.student.portal.model.Student;
import br.com.student.portal.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {


    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student createStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student updateStudent(UUID id, Student studentDetails){
        var student = studentRepository.findById(id).orElseThrow();
        student.setName(studentDetails.getName());
        student.setCourse(studentDetails.getCourse());
        return studentRepository.save(student);
        
    }

    public void deleteStudent (UUID id){
        var student = studentRepository.findById(id).orElseThrow();
        studentRepository.delete(student);
    }


}
