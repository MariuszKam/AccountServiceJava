package account.businesslayer.payment;

import account.businesslayer.exceptions.NegativeSalaryException;
import account.businesslayer.exceptions.PeriodExistsException;
import account.businesslayer.exceptions.UserDoesNotExistsException;
import account.persistencelayer.PaymentRepository;
import account.persistencelayer.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
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
        for (Payment payment: payroll
             ) {
            inputCheck(payment);
            if (paymentRepository.existsByEmployeeAndPeriod(payment.getEmployee(), payment.getPeriod())) {
                throw new PeriodExistsException();
            }
        }
        paymentRepository.saveAll(payroll);
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

    private void inputCheck(Payment payment) {
        if (!userExists(payment.getEmployee())) {
            throw new UserDoesNotExistsException();
        }
        if (!isMonth(payment.getPeriod())) {
            throw new PeriodExistsException();
        }
        if (!isNonNegativeSalary(payment.getSalary())) {
            throw new NegativeSalaryException();
        }
    }
}
