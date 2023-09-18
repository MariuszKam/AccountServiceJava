package account.businesslayer.payment;

import account.businesslayer.user.User;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class PaymentConverter {

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

    public PaymentDTO convertPayment(Payment payment, User user) {
        String period = periodConverter(payment.getPeriod());
        String salary = salaryConverter(payment.getSalary());

        return new PaymentDTO(user.getName(), user.getLastname(), period, salary);
    }

    public List<PaymentDTO> convertAll(List<Payment> payroll, User user) {
        return payroll.stream().
                map(payment -> convertPayment(payment, user))
                .collect(Collectors.toList());
    }
}
