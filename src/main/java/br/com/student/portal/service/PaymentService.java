package br.com.student.portal.service;

import br.com.student.portal.model.Payment;
import br.com.student.portal.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.LocalDate.now;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByStudentId(Long studentId) {
        return paymentRepository.findByStudentId(studentId);
    }

    public Payment createPayment(Payment payment) {
        payment.setPaymentDate(LocalDate.now());
        payment.setStatus("PAGO");
        return savePayment(payment);
    }

    public Payment createPendingPayment(Long studentId, Double amount, LocalDate dueDate) {
        return savePayment(new Payment(studentId, amount, dueDate, "PENDENTE"));
    }

    public Optional<Payment> registerPayment(Long paymentId, String method) {
        return paymentRepository.findById(paymentId).map(payment -> {
            payment.setStatus("PAGO");
            payment.setPaymentDate(LocalDate.now());
            payment.setPaymentMethod(method);
            return savePayment(payment);
        });
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOverduePayments() {
        paymentRepository.findByDueDateBeforeAndStatus(LocalDate.now(), "PENDENTE")
                .forEach(payment -> {
                    payment.setStatus("ATRASADO");
                    savePayment(payment);
                });
    }

    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    private Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}