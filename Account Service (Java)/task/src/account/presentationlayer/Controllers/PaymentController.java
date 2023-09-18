package account.presentationlayer.Controllers;

import account.businesslayer.payment.*;
import account.businesslayer.user.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/api/acct/payments")
    public ResponseEntity<Object> addPayroll(@RequestBody List<Payment> payroll) {
        paymentService.addAll(payroll);
        return new ResponseEntity<>(new PaymentStatusDTO("Added successfully!"), HttpStatus.OK);
    }

    @PutMapping("/api/acct/payments")
    public ResponseEntity<Object> updatePayroll(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
        return new ResponseEntity<>(new PaymentStatusDTO("Updated successfully!"), HttpStatus.OK);
    }

    @GetMapping("/api/empl/payment")
    public ResponseEntity<Object> payment(@AuthenticationPrincipal User user,
                                          @RequestParam(required = false) String period) {
        System.out.println("Here is period: " + period);
        if (period == null) {
            List<PaymentDTO> payrollView = paymentService.showPayroll(user);
            return new ResponseEntity<>(payrollView, HttpStatus.OK);
        }
        PaymentDTO paymentView = paymentService.showPayment(user, period);
        return new ResponseEntity<>(paymentView, HttpStatus.OK);
    }

}
