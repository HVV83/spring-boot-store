package com.codewithmosh.store.payments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
@Getter
public class WebhookRequest {
    private final Map<String, String> headers;
    private final String payload;

}
