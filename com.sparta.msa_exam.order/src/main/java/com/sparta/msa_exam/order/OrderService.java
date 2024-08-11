package com.sparta.msa_exam.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductClient productClient;

    @Transactional
    public Long create(OrderDto dto) {

        for (Long productId : dto.getProductIds()) {
            // FeignClient 호출로 상품 존재하는지 확인
            ProductDto productDto = productClient.isProductExist(productId);
            if (productDto == null)
                throw new IllegalArgumentException("존재하지 않는 상품입니다.");
        }

        Order order = Order.builder()
                .name(dto.getName())
                .build();

        Long orderId = orderRepository.save(order).getId();

        List<Long> orderProductIds = dto.getProductIds();
        for (Long orderProductId : orderProductIds) {
            OrderProduct orderProduct = OrderProduct.builder()
                    .productId(orderProductId)
                    .order(order)
                    .build();

            orderProductRepository.save(orderProduct);
            order.addOrderProduct(orderProduct);
        }
        return orderId;
    }

    @Transactional(readOnly = true)
    public OrderDto readOne(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return OrderDto.fromEntity(order);
    }

    @Transactional
    public Long update(Long orderId, Long orderProductId) {
        Order order = orderRepository.findById(orderId).orElseThrow();

        // FeignClient 호출로 상품 존재하는지 확인
        ProductDto productDto = productClient.isProductExist(orderProductId);
        if (productDto == null)
            throw new IllegalArgumentException("존재하지 않는 상품입니다.");

        OrderProduct orderProduct = OrderProduct.builder()
                .productId(orderProductId)
                .order(order)
                .build();
        orderProductRepository.save(orderProduct);

        order.addOrderProduct(orderProduct);
        return orderRepository.save(order).getId();
    }
}
