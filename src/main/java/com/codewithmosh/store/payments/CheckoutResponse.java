package com.codewithmosh.store.payments;

import lombok.Data;

@Data
public class CheckoutResponse {

    private final Long orderId;
    private final String checkoutUrl;

    public CheckoutResponse(Long orderId, String checkoutUrl) {
        this.orderId = orderId;
        this.checkoutUrl = checkoutUrl;
    }

}
