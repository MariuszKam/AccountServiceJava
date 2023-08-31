package account.presentationlayer.Controllers;

import account.businesslayer.exceptions.ErrorException;
import account.businesslayer.payment.*;
import account.businesslayer.user.User;
import account.businesslayer.user.UserMapper;
import account.persistencelayer.PaymentRepository;
import account.persistencelayer.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class PaymentController {
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final PaymentConverter paymentConverter;

    public PaymentController(UserRepository userRepository, PaymentRepository paymentRepository,
                             PaymentService paymentService, PaymentConverter paymentConverter) {
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
        this.paymentConverter = paymentConverter;
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
    public ResponseEntity<Object> payment(Principal principal,
                                          @RequestParam(required = false) String period) {
        if (period != null) {
            Payment payment = paymentRepository.findByEmployeeIgnoreCaseAndPeriod(principal.getName(), period);
            if (payment == null) {
                throw new ErrorException("Error!");
            }
            PaymentDTO paymentView = paymentConverter.userViewConverter(payment);
            return new ResponseEntity<>(paymentView, HttpStatus.OK);
        }
        List<Payment> payroll = paymentRepository.findAllByEmployeeIgnoreCase(principal.getName());
        List<PaymentDTO> payrollView = paymentConverter.convertAll(payroll);
        return new ResponseEntity<>(payrollView, HttpStatus.OK);
    }

}
