package am.itspace.sweetbakerystorecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Name can't be empty.")
    @Pattern(regexp = "\\D*", message = "Must not contain numbers")
    @Size(min = 3, max = 20)
    private String name;

    @NotNull(message = "Description can't be empty.")
    @Size(max = 1200)
    private String description;

    @ManyToOne
    private User user;

    public Category(String name) {
        this.name = name;
    }
}
