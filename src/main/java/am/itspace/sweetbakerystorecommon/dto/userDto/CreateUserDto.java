package am.itspace.sweetbakerystorecommon.dto.userDto;
import am.itspace.sweetbakerystorecommon.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    @Pattern(regexp = "\\D*", message = "Must not contain numbers")
    @NotBlank(message = "Name can't be empty.")
    @Size(min = 2, max = 30)
    private String name;

    @Pattern(regexp = "\\D*", message = "Must not contain numbers")
    @NotBlank(message = "Surname can't be empty.")
    @Size(min = 2, max = 30)
    private String surname;

    @NotBlank(message = "Email can't be empty.")
    @Email(message = "Email is not valid", regexp = "^.+@.+\\..+$")
    private String email;

    @NotBlank(message = "Password can't be empty.")
    private String password;

    @NotBlank(message = "Name can't be empty.")
    @Size(min = 9, max = 20)
    private String phone;
    private Address address;
    private Date createAt;


}
