package am.itspace.sweetbakerystorecommon.dto.orderDto;

import am.itspace.sweetbakerystorecommon.entity.OrderStatus;
import am.itspace.sweetbakerystorecommon.entity.Payment;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class OrderResponseDto {

    private Date orderDate;

    private int count;

    private OrderStatus orderStatus;

    private User user;

    private Product product;

    private Payment payment;

}
