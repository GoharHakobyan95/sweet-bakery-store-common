package am.itspace.sweetbakerystorecommon.dto.basketDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketRequest {
    private Integer product_id;
    private int quantity;
}
