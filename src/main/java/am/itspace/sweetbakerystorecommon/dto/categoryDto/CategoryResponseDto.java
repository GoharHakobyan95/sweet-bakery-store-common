package am.itspace.sweetbakerystorecommon.dto.categoryDto;

import am.itspace.sweetbakerystorecommon.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {

    private int Id;
    private String name;
    private String description;
    private User user;
}
