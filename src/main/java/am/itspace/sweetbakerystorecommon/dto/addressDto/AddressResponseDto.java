package am.itspace.sweetbakerystorecommon.dto.addressDto;

import am.itspace.sweetbakerystorecommon.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDto {
    private int id;
    private String name;
    private City city;

}
