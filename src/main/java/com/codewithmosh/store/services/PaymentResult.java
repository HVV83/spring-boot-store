package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.PaymentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PaymentResult {
    private final Long orderId;
    private final PaymentStatus paymentStatus;

}
