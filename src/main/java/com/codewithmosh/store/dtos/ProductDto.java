package com.codewithmosh.store.dtos;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ProductDto {

    Long id;
    String name;
    String description;
    BigDecimal price;
    Byte categoryId;

}
