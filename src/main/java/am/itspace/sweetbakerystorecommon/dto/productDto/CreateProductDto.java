package am.itspace.sweetbakerystorecommon.dto.productDto;

import am.itspace.sweetbakerystorecommon.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductDto {

    @NotBlank(message = "Name can't be empty.")
    @Size(min = 3, max = 20)
    private String name;

    @NotBlank(message = "Description can't be empty.")
    @Size(max = 1000)
    private String description;

    @Min(value = 0, message = "Min price can't be lower then 0")
    private double price;

    @Min(value = 0, message = "Quantity can't be lower then 0")
    private int inStore;

    private String productPic;

    @ManyToOne
    private Category category;

}
