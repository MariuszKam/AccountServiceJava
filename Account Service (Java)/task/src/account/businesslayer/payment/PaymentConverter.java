package account.businesslayer.payment;

import account.businesslayer.user.User;
import account.persistencelayer.UserRepository;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Component
public class PaymentConverter {
    private final UserRepository userRepository;

    public PaymentConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private String salaryConverter(Long salary) {
        String salaryString = Long.toString(salary);
        String dollars = salaryString.substring(0, salaryString.length() - 2);
        String cents = salaryString.substring(salaryString.length() - 2);
        if (dollars.isEmpty()) {
            dollars = "0";
        }
        return dollars + " dollar(s) " + cents + " cent(s)";
    }

    private String periodConverter(String period) {
        String[] parts = period.split("-");
        Month month = Month.of(Integer.parseInt(parts[0]));
        return month.getDisplayName(TextStyle.FULL, Locale.ENGLISH) + "-" + parts[1];
    }

    public PaymentDTO userViewConverter (Payment payment) {
        User user = userRepository.findByEmailIgnoreCase(payment.getEmployee());
        String period = periodConverter(payment.getPeriod());
        String salary = salaryConverter(payment.getSalary());
        
        return new PaymentDTO(user.getName(), user.getLastname(), period, salary);
    }
    
    public List<PaymentDTO> convertAll (List<Payment> payroll) {
        List<PaymentDTO> result = new ArrayList<>();
        for (Payment payment:payroll
             ) {
            result.add(userViewConverter(payment));
        }
        Collections.reverse(result);
        return result;
    }
}
