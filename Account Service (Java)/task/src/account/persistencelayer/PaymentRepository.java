package account.persistencelayer;

import account.businesslayer.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByEmployeeIgnoreCaseOrderByPeriodDesc(String employee);

    Payment findByEmployeeIgnoreCaseAndPeriod(String employee, String period);

    boolean existsByEmployeeAndPeriod(String employee, String period);


}
