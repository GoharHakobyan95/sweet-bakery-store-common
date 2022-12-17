package am.itspace.sweetbakerystorecommon.dto.basketDto;

import am.itspace.sweetbakerystorecommon.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@NoArgsConstructor
public class BasketProductDto {
    private Product product;
    private int quantity;

    public BasketProductDto(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

}
