package am.itspace.sweetbakerystorecommon.dto.addressDto;

import am.itspace.sweetbakerystorecommon.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressDto {
    private String name;
    private City city;
}
