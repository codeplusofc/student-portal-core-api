package br.com.student.portal.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
public class Student {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;

    private String registration;

    private String course;




}
