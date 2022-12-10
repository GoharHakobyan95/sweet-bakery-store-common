package am.itspace.sweetbakerystorecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
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

    @ManyToOne
    private User user;

}
