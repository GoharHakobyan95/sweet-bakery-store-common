package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.BasketDto;
import am.itspace.sweetbakerystorecommon.dto.orderDto.CheckoutDto;
import am.itspace.sweetbakerystorecommon.dto.paymentDto.CreatePaymentDto;
import am.itspace.sweetbakerystorecommon.dto.paymentDto.PaymentResponseDto;
import am.itspace.sweetbakerystorecommon.dto.paymentDto.UpdatePaymentDto;
import am.itspace.sweetbakerystorecommon.entity.Payment;
import am.itspace.sweetbakerystorecommon.entity.Product;
import am.itspace.sweetbakerystorecommon.entity.Status;
import am.itspace.sweetbakerystorecommon.entity.User;
import am.itspace.sweetbakerystorecommon.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductService productService;
    private final OrderService orderService;

    private final ModelMapper modelMapper;

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

    public Payment saveCard(CreatePaymentDto createPaymentDto, User user) {
        Payment payment = Payment.builder()
                .cardNumber(createPaymentDto.getCardNumber())
                .cardType(createPaymentDto.getCardType())
                .cvcCode(createPaymentDto.getCvcCode())
                .expirationDate(createPaymentDto.getExpirationDate())
                .status(createPaymentDto.getStatus())
                .user(user)
                .build();
        Payment savedCard = paymentRepository.save(payment);
        return modelMapper.map(savedCard, Payment.class);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> findById(int id) {
        return paymentRepository.findById(id);
    }

    public Optional<List<Payment>> findByUserId(User user) {
        List<Payment> paymentsByUser_id = paymentRepository.findPaymentsByUser_Id(user.getId());
        return Optional.of(paymentsByUser_id);
    }


    public PaymentResponseDto update(Payment payment, UpdatePaymentDto updatePaymentDto, User user) {
        payment.setId(updatePaymentDto.getId());
        payment.setCardNumber(updatePaymentDto.getCardNumber());
        payment.setCvcCode(updatePaymentDto.getCvcCode());
        payment.setStatus(updatePaymentDto.getStatus());
        payment.setExpirationDate(updatePaymentDto.getExpirationDate());
        payment.setUser(user);
        Payment savedPayment = paymentRepository.save(payment);
        return modelMapper.map(savedPayment, PaymentResponseDto.class);
    }

    public void deleteById(int id) {
        paymentRepository.deleteById(id);
    }
}