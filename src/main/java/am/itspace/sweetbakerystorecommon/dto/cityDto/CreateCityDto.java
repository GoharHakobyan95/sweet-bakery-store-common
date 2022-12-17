package am.itspace.sweetbakerystorecommon.dto.cityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCityDto {

    @NotEmpty(message = "Name can't be empty.")
    @Size(min = 2, max = 20)
    private String name;
}
