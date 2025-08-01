package br.com.student.portal.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String registration;
    private String course;

    public StudentEntity(String name, String course) {
        this.name = name;
        this.course = course;
    }

    @PrePersist
    private void prePersist() {
        if (this.registration == null) {
            this.registration = generateRegistrationNumber();
        }
    }

    private String generateRegistrationNumber() {
        return UUID.randomUUID().toString().split("-")[0]
                .toUpperCase();
    }
}
