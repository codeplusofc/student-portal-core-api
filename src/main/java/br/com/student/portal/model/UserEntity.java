package br.com.student.portal.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String email;

}
