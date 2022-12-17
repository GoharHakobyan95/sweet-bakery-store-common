package am.itspace.sweetbakerystorecommon.dto.paymentDto;

import am.itspace.sweetbakerystorecommon.entity.CardType;
import am.itspace.sweetbakerystorecommon.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private String cardNumber;

    private String cvcCode;

    private Date expirationDate;

    private Status status;

    private CardType cardType;

    private boolean isGift;

    private String wishNotes;
}
