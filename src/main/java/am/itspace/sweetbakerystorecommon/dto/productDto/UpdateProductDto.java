package am.itspace.sweetbakerystorecommon.dto.productDto;

import am.itspace.sweetbakerystorecommon.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private int inStore;
    private String productPic;
    private Category category;




}
