package br.com.student.portal.service;


import br.com.student.portal.dto.student.StudentRequest;
import br.com.student.portal.dto.student.StudentResponse;
import br.com.student.portal.entity.StudentEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.student.portal.validation.StudentValidator.validateFields;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponse createStudent(StudentRequest studentRequest) {
        var student = new StudentEntity(studentRequest.getRegistration(),
                studentRequest.getCourse(),
                studentRequest.getName());

        validateFields(student);

        var studentSaved = studentRepository.save(student);

        return new StudentResponse(studentSaved.getId(),
                studentSaved.getName(),
                studentSaved.getCourse());
    }

    public List<StudentResponse> getAllStudents() {
        var students = studentRepository.findAll();

        if (students.isEmpty()) {
            throw new ObjectNotFoundException("No students found");
        }

        return students.stream()
                .map(studentEntity -> new StudentResponse(studentEntity.getId(),
                        studentEntity.getName(),
                        studentEntity.getCourse())).collect(Collectors.toList());

    }

    public StudentResponse updateStudent(UUID id, StudentRequest studentRequest) {
        var student = findStudentById(id);

        student.setName(studentRequest.getName());
        student.setCourse(studentRequest.getCourse());

        var studentSaved = studentRepository.save(student);
        return new StudentResponse(studentSaved.getId(),
                studentSaved.getName(),
                studentSaved.getCourse());
    }

    public void deleteStudent(UUID id) {
        var student = findStudentById(id);

        studentRepository.delete(student);
    }

    private StudentEntity findStudentById(UUID id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User with id " + id + " not found"));
    }


}
