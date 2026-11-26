package ra.edu.ss15.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ra.edu.ss15.dto.OrderItemRequest;
import ra.edu.ss15.dto.OrderRequest;
import ra.edu.ss15.entity.Order;
import ra.edu.ss15.entity.OrderItem;
import ra.edu.ss15.entity.Product;
import ra.edu.ss15.entity.User;
import ra.edu.ss15.repository.OrderRepository;
import ra.edu.ss15.repository.ProductRepository;
import ra.edu.ss15.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public Order createOrder(String userEmail, OrderRequest request) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + userEmail));

        Order order = Order.builder()
                .user(user)
                .createdDate(LocalDateTime.now())
                .status("PENDING")
                .totalMoney(BigDecimal.ZERO)
                .items(new ArrayList<>())
                .build();

        BigDecimal totalMoney = BigDecimal.ZERO;

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy sản phẩm ID: " + itemReq.getProductId()));

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalMoney = totalMoney.add(itemTotal);

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .priceBuy(product.getPrice())
                    .build();

            order.getItems().add(orderItem);
        }

        order.setTotalMoney(totalMoney);
        return orderRepository.save(order);
    }

    public List<Order> getMyOrders(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy người dùng: " + userEmail));
        return orderRepository.findByUserOrderByCreatedDateDesc(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + orderId));
        order.setStatus(status.toUpperCase());
        return orderRepository.save(order);
    }
}
