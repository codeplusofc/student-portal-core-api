package br.com.student.portal.service;


import br.com.student.portal.entity.StudentEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
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

    public StudentEntity createStudent(StudentEntity studentEntity) {
        return studentRepository.save(studentEntity);
    }

    public List<StudentEntity> getAllStudents() {
        var students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new ObjectNotFoundException("No students found");
        }

        return students;
    }

    public StudentEntity updateStudent(UUID id, StudentEntity studentEntity) {
        var student = findStudentById(id);

        student.setName(studentEntity.getName());
        student.setCourse(studentEntity.getCourse());

        return studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {
        var student = findStudentById(id);

        studentRepository.delete(student);
    }

    public StudentEntity findStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
    }

}
