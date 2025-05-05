package br.com.student.portal.service;

import br.com.student.portal.entity.Payment;
import br.com.student.portal.exception.ObjectNotFoundException;
import br.com.student.portal.repository.PaymentRepository;
import br.com.student.portal.repository.StudentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByStudentId(UUID studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public Payment createPayment(UUID studentId, Payment payment) {
        var student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ObjectNotFoundException("Aluno não encontrado com ID: " + studentId));

        payment.setStudent(student);
        payment.setPaymentDate(null);
        payment.setStatus("PENDENTE");

        return savePayment(payment);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOverduePayments() {
        paymentRepository.findByDueDateBeforeAndStatus(LocalDate.now(), "PENDENTE")
                .forEach(payment -> {
                    payment.setStatus("ATRASADO");
                    savePayment(payment);
                });
    }

    public Payment markAsPaid(UUID paymentId) {
        var payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        payment.setStatus("PAGO");
        payment.setPaymentDate(LocalDate.now());

        return paymentRepository.save(payment);
    }

    public void deletePayment(UUID id) {
        paymentRepository.deleteById(id);
    }

    private Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}