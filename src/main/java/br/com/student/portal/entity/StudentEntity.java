package br.com.student.portal.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String registration;
    private String course;

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
