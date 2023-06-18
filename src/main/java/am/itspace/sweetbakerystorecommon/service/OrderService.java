package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.BasketDto;
import am.itspace.sweetbakerystorecommon.dto.BasketProductDto;
import am.itspace.sweetbakerystorecommon.dto.orderDto.CheckoutDto;
import am.itspace.sweetbakerystorecommon.dto.orderDto.CreateOrderDto;
import am.itspace.sweetbakerystorecommon.entity.*;
import am.itspace.sweetbakerystorecommon.repository.OrderRepository;
import am.itspace.sweetbakerystorecommon.repository.PaymentRepository;
import am.itspace.sweetbakerystorecommon.repository.ProductRepository;
import am.itspace.sweetbakerystorecommon.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private final PaymentRepository paymentRepository;

    @Resource(name = "basketDto")
    private BasketDto basketDto;
    @Value("${sweet.bakery.store.images.folder}")
    private String folderPath;

    public byte[] getProductImage(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
        return IOUtils.toByteArray(inputStream);
    }

    public Page<Order> findPaginated(Pageable pageable, CurrentUser currentUser) {
        Page<Order> allOrders = orderRepository.findOrdersByUser_Id(currentUser.getUser().getId(),pageable);
        return new PageImpl<>(allOrders.getContent(), pageable, allOrders.getSize());
    }

    @Transactional(readOnly = true)
    public Long getCountOfOrders() {
        return orderRepository.count();
    }


    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    // saveOrder is used to save order for a single or multiple products selected by the user
    public void saveOrder(CheckoutDto checkoutDto, Payment payment, User user) {

        Optional<Product> product = productRepository.findById(checkoutDto.getProductId());
        if (product.isPresent()) {
            Order singleOrder = Order.builder()
                    .user(user)
                    .product(product.get())
                    .orderDate(new Date())
                    .payment(payment)
                    .wishNotes(checkoutDto.getWishNotes())
                    .count(checkoutDto.getQuantity())
                    .address(user.getAddress())
                    .orderStatus(OrderStatus.DONE)
                    .isGift(checkoutDto.isGift()).build();
            orderRepository.save(singleOrder);
        } else {
            createOrderFromBasket(checkoutDto, payment, user);
        }
    }

    // createOrderFromBasket is used to save order for multiple products selected by the user from Basket
    private void createOrderFromBasket(CheckoutDto checkoutDto, Payment payment, User user) {
        basketDto.getBasketProductDtos().forEach(basketProductDto -> {
            Order multipleOrder = Order.builder()
                    .user(user)
                    .product(basketProductDto.getProduct())
                    .orderDate(new Date())
                    .payment(payment)
                    .wishNotes(checkoutDto.getWishNotes())
                    .count(basketProductDto.getQuantity())
                    .address(user.getAddress())
                    .orderStatus(OrderStatus.DONE)
                    .isGift(checkoutDto.isGift()).build();
            orderRepository.save(multipleOrder);
        });
    }


    public Order saveOrder(User user, CreateOrderDto createOrderDto, Product product, Integer quantity) {
        Optional<Payment> paymentByUserId = paymentRepository.findById(user.getId());
        Order newOrder = Order.builder()
                .product(createOrderDto.getProduct())
                .orderStatus(OrderStatus.IN_PROCESS)
                .orderDate(new Date())
                .count(createOrderDto.getCount())
                .isGift(createOrderDto.isGift())
                .wishNotes(createOrderDto.getWishNotes())
                .user(user)
                .address(user.getAddress())
                .payment(paymentByUserId.get())
                .build();
        Order finalOrder = orderRepository.save(newOrder);
        product.setInStore(product.getInStore() - quantity);
        productRepository.save(product);
        return finalOrder;
    }

    public void delete(Order order) {
        orderRepository.delete(order);
    }
}
