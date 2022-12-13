package am.itspace.sweetbakerystorecommon.dto.userDto;
import am.itspace.sweetbakerystorecommon.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private Address address;
//    private String profilePic;
//    private boolean isActive;
    private Date createAt;
//    private String verifyToken;

}
