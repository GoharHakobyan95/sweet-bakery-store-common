package am.itspace.sweetbakerystorecommon.repository;
import am.itspace.sweetbakerystorecommon.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    long count();

}
