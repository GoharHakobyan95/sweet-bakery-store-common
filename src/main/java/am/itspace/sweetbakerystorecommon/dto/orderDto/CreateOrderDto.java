package am.itspace.sweetbakerystorecommon.dto.orderDto;

import am.itspace.sweetbakerystorecommon.entity.OrderStatus;
import am.itspace.sweetbakerystorecommon.entity.Payment;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CreateOrderDto {

    private Date orderDate;

    @Min(value = 1,message = "Please choose a product")
    private int count;

    private boolean isGift;

    private String wishNotes;

    private OrderStatus orderStatus;

    private Product product;


}
