package com.codewithmosh.store.dtos.cart;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartProductDto {

    private Long id;
    private String name;
    private BigDecimal price;

}
