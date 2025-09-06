package br.com.student.portal.service;

import br.com.student.portal.dto.student.StudentRequest;
import br.com.student.portal.dto.student.StudentResponse;
import br.com.student.portal.entity.StudentEntity;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.StudentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static br.com.student.portal.data.FixedData.*;
import static br.com.student.portal.validation.StudentValidator.validateFields;
import static com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentService studentService;

    private StudentEntity studentEntity;
    private StudentRequest studentRequest;


    @Before
    public void setup() {
        this.studentRequest = new StudentRequest("Otavio", STUDENT_REGISTRATION, "SPRING BOOT");
        this.studentEntity = new StudentEntity(STUDENT_ID, studentRequest.getRegistration(), studentRequest.getName(), studentRequest.getCourse());
    }

    @Test
    public void mustCreateStudent() {
        given(studentRepository.save(any(StudentEntity.class)))
                .willReturn(studentEntity);
        var result = studentService.createStudent(studentRequest);

        assertEquals("Otavio", result.getName());
        assertEquals("SPRING BOOT", result.getCourse());
        assertEquals("922e4567-e89b-12d3-a456-426614174000", result.getId().toString());
        assertEquals("912e4567-e89b-12d3-a456-426614174000", result.getRegistration());
    }


//    public StudentResponse createStudent(StudentRequest studentRequest) {
//        var student = new StudentEntity(studentRequest.getName(),
//                studentRequest.getCourse());
//
//        validateFields(student);
//
//        var studentSaved = studentRepository.save(student);
//
//        return new StudentResponse(studentSaved.getId(),
//                studentSaved.getName(),
//                studentSaved.getCourse(), studentSaved.getRegistration());
//    }






    @Test
    public void mustNotFindStudents(){
        given(studentRepository.findAll()).willReturn(List.of());
        assertThrows(ObjectNotFoundException.class, () -> {
            studentService.getAllStudents();
        });
    }


    @Test
    public void mustFindAllStudents() {
        var students = List.of(studentEntity, studentEntity);
        given(studentRepository.findAll()).willReturn(students);

        var result = studentService.getAllStudents();

        assertEquals(2, result.size());
        assertEquals("Otavio", result.get(0).getName());
        assertEquals("SPRING BOOT", result.get(0).getCourse());
        assertEquals("922e4567-e89b-12d3-a456-426614174000", result.get(0).getId().toString());
        assertEquals("Otavio", result.get(1).getName());
        assertEquals("SPRING BOOT", result.get(1).getCourse());
        assertEquals("922e4567-e89b-12d3-a456-426614174000" , result.get(1).getId().toString());
    }

    @Test
    public void mustUpdateStudent(){
        given(studentRepository.findById(STUDENT_ID)).willReturn(Optional.ofNullable(studentEntity));
        given(studentRepository.save(studentEntity)).willReturn(studentEntity);
        var toUpdate = new StudentRequest("Pedrin", STUDENT_REGISTRATION, "Cursinho top");
        var result = studentService.updateStudent(STUDENT_ID, toUpdate);

        assertEquals("Pedrin", result.getName());
        assertEquals(STUDENT_REGISTRATION, result.getRegistration());
        assertEquals("Cursinho top", result.getCourse());
    }
    @Test
    public void mustDeleteStudent(){
        given(studentRepository.findById(STUDENT_ID)).willReturn(Optional.ofNullable(studentEntity));
        studentService.deleteStudent(STUDENT_ID);
        verify(studentRepository, times(1)).delete(studentEntity);

    }
}











