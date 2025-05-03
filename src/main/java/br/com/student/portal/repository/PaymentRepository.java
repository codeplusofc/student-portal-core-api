package br.com.student.portal.repository;

import br.com.student.portal.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findByStudentId(UUID studentId);

    List<Payment> findByDueDateBeforeAndStatus(LocalDate date, String status);
}
