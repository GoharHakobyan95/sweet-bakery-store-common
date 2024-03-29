package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.basketDto.BasketDto;
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
import org.springframework.data.domain.PageImpl;
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

    //Users fill in card details, before each order
    public void save(CheckoutDto checkoutDto, User user) {
        Payment payment = Payment.builder()
                .user(user)
                .status(Status.PAYED)
                .cardNumber(checkoutDto.getCardNumber())
                .cvcCode(checkoutDto.getCvcCode())
                .cardType(checkoutDto.getCardType())
                .expirationDate(checkoutDto.getExpirationDate())
                .build();

        if (checkoutDto.getProductId() != null) {
            Optional<Product> productById = productService.findById(checkoutDto.getProductId());
            productById.ifPresent(product -> payment.setTotalAmount(checkoutDto.getQuantity() * product.getPrice()));
        } else {
            payment.setTotalAmount(basketDto.getTotal());
        }

        Payment savedPayment = paymentRepository.save(payment);
        orderService.saveOrder(checkoutDto, savedPayment, user);
    }


    public Payment saveCard(CreatePaymentDto createPaymentDto, User user) {
        Payment payment = Payment.builder()
                .user(user)
                .cardNumber(createPaymentDto.getCardNumber())
                .cardType(createPaymentDto.getCardType())
                .cvcCode(createPaymentDto.getCvcCode())
                .expirationDate(createPaymentDto.getExpirationDate())
                .status(createPaymentDto.getStatus())
                .build();
        Payment savedCard = paymentRepository.save(payment);
        return modelMapper.map(savedCard, Payment.class);
    }

    public Optional<Payment> findById(int id) {
        return paymentRepository.findById(id);
    }

    public Optional<List<Payment>> findByUserId(User user) {
        List<Payment> paymentsByUser_id = paymentRepository.findPaymentsByUser_Id(user.getId());
        return Optional.of(paymentsByUser_id);
    }


    public PaymentResponseDto update(UpdatePaymentDto updatePaymentDto, User user) {
        Payment payment = Payment.builder()
                .id(updatePaymentDto.getId())
                .cardNumber(updatePaymentDto.getCardNumber())
                .cvcCode(updatePaymentDto.getCvcCode())
                .status(updatePaymentDto.getStatus())
                .expirationDate(updatePaymentDto.getExpirationDate())
                .user(user)
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        return modelMapper.map(savedPayment, PaymentResponseDto.class);
    }

    public Page<Payment> findPaginated(Pageable pageable) {
        Page<Payment> paymentPages = paymentRepository.findAll(pageable);
        return new PageImpl<>(paymentPages.getContent(), pageable, paymentPages.getSize());
    }

    public void deleteById(int id) {
        paymentRepository.deleteById(id);
    }

}