package am.itspace.sweetbakerystorecommon.dto.productDto;
import am.itspace.sweetbakerystorecommon.entity.Category;
import am.itspace.sweetbakerystorecommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private int id;
    private String name;
    private String description;
    private double price;
    private int inStore;
    private String productPic;
    private Category category;
    private User user;

}
