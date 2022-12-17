package am.itspace.sweetbakerystorecommon.dto.categoryDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryDto {

    @NotBlank(message = "Name can't be empty.")
    @Pattern(regexp = "\\D*", message = "Must not contain numbers")
    @Size(min = 3, max = 20)
    private String name;
    @NotNull(message = "Description can't be empty.")
    @Size(max = 1200)
    private String description;

}
