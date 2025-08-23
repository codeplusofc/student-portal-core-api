package br.com.student.portal.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data

public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private LocalDateTime deadline;
    private String description;
    private String title;
    private boolean received;
    private String course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

}
