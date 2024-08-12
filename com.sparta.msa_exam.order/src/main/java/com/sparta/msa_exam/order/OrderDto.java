package com.sparta.msa_exam.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto implements Serializable {
    private Long id;
    private String name;
    private List<Long> productIds = new ArrayList<>();

    public static OrderDto fromEntity(Order order) {

        List<Long> productIds = order.getOrderProductIds().stream()
                .map(OrderProduct::getProductId)
                .collect(Collectors.toList());

        return OrderDto.builder()
                .id(order.getId())
                .productIds(productIds)
                .build();
    }
}
