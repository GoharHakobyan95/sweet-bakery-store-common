package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    List<Payment> findPaymentsByUser_Id(int userId);
     Payment findByUserId(int userId);
}
