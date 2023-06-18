package am.itspace.sweetbakerystorecommon.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @ManyToOne
    private Address address;

    private String profilePic;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isActive;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    private String verifyToken;

    public User(String ...name) {
        this.name= String.valueOf(name);
    }
}