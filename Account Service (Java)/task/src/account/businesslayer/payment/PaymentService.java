package account.businesslayer.payment;


import account.businesslayer.user.User;
import account.persistencelayer.PaymentRepository;
import account.persistencelayer.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final PaymentConverter paymentConverter;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository,
                          PaymentConverter paymentConverter) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.paymentConverter = paymentConverter;
    }

    @Transactional
    public void updatePayment(Payment payment) {
        inputCheck(payment);
        Payment loadPayment = paymentRepository
                .findByEmployeeIgnoreCaseAndPeriod(payment.getEmployee(), payment.getPeriod());
        loadPayment.setSalary(payment.getSalary());
        paymentRepository.save(loadPayment);
    }

    @Transactional
    public void addAll(List<Payment> payroll) {
        for (Payment payment : payroll
        ) {
            inputCheck(payment);
        }
        paymentRepository.saveAll(payroll);
    }

    public PaymentDTO showPayment(User user, String period) {
        if (!isMonth(period)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error!");
        }
        Payment payment = paymentRepository.findByEmployeeIgnoreCaseAndPeriod(user.getEmail(), period);
        return paymentConverter.convertPayment(payment, user);
    }

    public List<PaymentDTO> showPayroll(User user) {
        List<Payment> payment = paymentRepository.findAllByEmployeeIgnoreCaseOrderByPeriodDesc(user.getEmail());
        return paymentConverter.convertAll(payment, user);
    }

    private boolean isMonth(String period) {
        String[] parts = period.split("-");
        int result = Integer.parseInt(parts[0]);
        return result > 0 && result < 13;
    }

    private boolean userExists(String employee) {
        return userRepository.existsByEmailIgnoreCase(employee);
    }

    private boolean isNonNegativeSalary(Long salary) {
        return salary > 0;
    }

    private boolean isPeriodExists(String employee, String period) {
        return paymentRepository.existsByEmployeeAndPeriod(employee, period);
    }

    private void inputCheck(Payment payment) {
        if (!userExists(payment.getEmployee())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee does not exist");
        }
        if (!isMonth(payment.getPeriod()) || isPeriodExists(payment.getEmployee(), payment.getPeriod())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Period already exists for this employee");
        }
        if (!isNonNegativeSalary(payment.getSalary())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Salary is negative");
        }
    }
}
