package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.BasketDto;
import am.itspace.sweetbakerystorecommon.dto.CheckoutDto;
import am.itspace.sweetbakerystorecommon.entity.Payment;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.Status;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final OrderService orderService;

    @Resource(name = "basketDto")
    private BasketDto basketDto;

    public Page<Payment> findPaginated(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }


    //Before each order user fill in card details
    public void save(CheckoutDto checkoutDto, User user) {
        Payment payment = new Payment();
        payment.setCardNumber(checkoutDto.getCardNumber());
        payment.setCvcCode(checkoutDto.getCvcCode());
        payment.setStatus(Status.PAYED);
        payment.setCardType(checkoutDto.getCardType());
        payment.setExpirationDate(checkoutDto.getExpirationDate());
        payment.setUser(user);
        if (checkoutDto.getProductId() != null) {
            Optional<Product> productById = productService.findById(checkoutDto.getProductId());
            productById.ifPresent(product -> payment.setTotalAmount(checkoutDto.getQuantity() * product.getPrice()));
        } else {
            payment.setTotalAmount(basketDto.getTotal());
        }
        Payment savedPayment = paymentRepository.save(payment);
        orderService.save(checkoutDto, savedPayment, user);
    }
}
