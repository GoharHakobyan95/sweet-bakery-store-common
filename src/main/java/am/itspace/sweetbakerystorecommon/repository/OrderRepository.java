package am.itspace.sweetbakerystorecommon.repository;

import am.itspace.sweetbakerystorecommon.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    long count();
    Page <Order> findOrdersByUser_Id(int userId);
}
