package br.com.student.portal.controller;

import br.com.student.portal.model.Payment;
import br.com.student.portal.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        var payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/student/{studentId}")
    public List<Payment> getPaymentsByStudentId(@PathVariable Long studentId) {
        return paymentService.getPaymentsByStudentId(studentId);
    }

    @PostMapping
    public Payment createPendingPayment(@RequestParam Long studentId,
                                        @RequestParam Double amount,
                                        @RequestParam String dueDate) {
        return paymentService.createPendingPayment(studentId, amount, LocalDate.parse(dueDate));
    }

    @PostMapping
    public Payment createPayment(@RequestBody Payment payment) {
        return paymentService.createPayment(payment);
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Payment> registerPayment(@PathVariable Long id, @RequestParam String method) {
        return paymentService.registerPayment(id, method)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
