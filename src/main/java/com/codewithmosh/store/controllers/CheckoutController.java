package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.checkout.CheckoutRequest;
import com.codewithmosh.store.dtos.checkout.CheckoutResponse;
import com.codewithmosh.store.entities.Cart;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
import com.codewithmosh.store.repositories.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.codewithmosh.store.entities.OrderStatus.PENDING;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @Valid @RequestBody CheckoutRequest request
    ) {
        Cart cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Cart not found")
            );
        }

        if (cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Cart is empty")
            );
        }

        Order order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getItems().forEach(item -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }

}
