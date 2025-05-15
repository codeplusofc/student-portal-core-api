package br.com.student.portal.entity;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Course {
    private UUID id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String status;

}