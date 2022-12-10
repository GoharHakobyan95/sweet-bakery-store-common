package am.itspace.sweetbakerystorecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Size(max = 16, min = 16, message = "Length must be 16.")
    @Pattern(regexp = "^\\d+$", message = "Card number's must contain only digits.")
    @NotNull(message = "Number of card can't be empty.")
    private String cardNumber;

    @Size(max = 3, min = 3, message = "Length must be 3.")
    @Pattern(regexp = "^\\d+$", message = "CVC code must contain only digits.")
    @NotNull(message = "CVC  can't be empty.")
    private String cvcCode;

    @NotNull(message = "Expiration Date can't be empty.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private CardType cardType;

    private double totalAmount;
}
