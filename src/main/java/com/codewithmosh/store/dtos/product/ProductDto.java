package com.codewithmosh.store.dtos.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;

}
