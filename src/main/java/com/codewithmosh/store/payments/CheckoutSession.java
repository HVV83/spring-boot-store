package com.codewithmosh.store.payments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CheckoutSession {

    private final String checkoutUrl;

}
