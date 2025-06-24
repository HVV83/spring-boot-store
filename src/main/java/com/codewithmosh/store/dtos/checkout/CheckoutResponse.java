package com.codewithmosh.store.dtos.checkout;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CheckoutResponse {

    private final Long orderId;

}
