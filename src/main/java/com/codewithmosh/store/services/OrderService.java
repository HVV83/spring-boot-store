package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.cart.OrderDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mappers.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        User user = authService.getCurrentUser();
        List<Order> orders = orderRepository.getAllByCustomer(user);

        return orders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

}
