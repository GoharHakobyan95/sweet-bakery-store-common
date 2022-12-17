package am.itspace.sweetbakerystorecommon.service;

import am.itspace.sweetbakerystorecommon.dto.BasketDto;
import am.itspace.sweetbakerystorecommon.dto.BasketProductDto;
import am.itspace.sweetbakerystorecommon.dto.orderDto.CheckoutDto;
import am.itspace.sweetbakerystorecommon.dto.orderDto.CreateOrderDto;
import am.itspace.sweetbakerystorecommon.entity.*;
import am.itspace.sweetbakerystorecommon.repository.OrderRepository;
import am.itspace.sweetbakerystorecommon.repository.PaymentRepository;
import am.itspace.sweetbakerystorecommon.repository.ProductRepository;
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
import java.util.List;
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

    @Transactional(readOnly = true)
    public Long getCountOfOrders() {
        return orderRepository.count();
    }


    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    public void save(CheckoutDto checkoutDto, Payment payment, User user) {
        //get product id when the customer making an order
        if (checkoutDto.getProductId() != null) {
            Optional<Product> productByID = productRepository.findById(checkoutDto.getProductId());
            //add currentUser address and card details to order, when user buy a single product
            Order finalOrder = Order.builder()
                    .user(user)
                    .product(productByID.get())
                    .orderDate(new Date())
                    .payment(payment)
                    .wishNotes(checkoutDto.getWishNotes())
                    .count(checkoutDto.getQuantity())
                    .address(user.getAddress())
                    .orderStatus(OrderStatus.DONE)
                    .isGift(checkoutDto.isGift()).build();
            orderRepository.save(finalOrder);
        } else {
            for (BasketProductDto basketProductDto : basketDto.getBasketProductDtos()) {
                //add currentUser address and card details to order,when user buy products from his basket
                Order finalOrder = Order.builder()
                        .user(user)
                        .product(basketProductDto.getProduct())
                        .orderDate(new Date())
                        .payment(payment)
                        .wishNotes(checkoutDto.getWishNotes())
                        .count(basketProductDto.getQuantity())
                        .address(user.getAddress())
                        .orderStatus(OrderStatus.DONE)
                        .isGift(checkoutDto.isGift()).build();
                orderRepository.save(finalOrder);

            }
        }
    }

    public List<Order> getAllOrders(User user) {
        return orderRepository.findOrdersByUser_Id(user.getId());
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
