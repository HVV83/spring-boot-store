package com.codewithmosh.store.payments;

import com.codewithmosh.store.entities.PaymentStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class PaymentResult {
    private final Long orderId;
    private final PaymentStatus paymentStatus;

}
