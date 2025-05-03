package br.com.student.portal.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID studentId;
    private Double amount;
    private LocalDate paymentDate;
    private LocalDate dueDate;
    private String status;
    private String paymentMethod;

    public Payment(UUID studentId, Double amount, LocalDate dueDate, String status, String paymentMethod) {
        this.studentId = studentId;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
        this.paymentDate = null;
        this.paymentMethod = paymentMethod;
    }
}
