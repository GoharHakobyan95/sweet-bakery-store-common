package am.itspace.sweetbakerystorecommon.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@Builder
@Data
@Entity
@NoArgsConstructor
@Table(name = "user_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;
    private int count;
    private boolean isGift;
    private String wishNotes;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
    @ManyToOne
    private Payment payment;
    @ManyToOne
    private Address address;
}
