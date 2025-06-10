package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.ProductDto;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductDto> getAllProducts(
            @RequestParam(name = "categoryId", required = false) Byte categoryId
    ) {
        List<Product> foundProducts = (categoryId == null) ?
                productRepository.findAllWithCategory() :
                productRepository.findByCategoryId(categoryId);

        return foundProducts.stream()
                .map(productMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(
            @RequestParam(name = "id") Long id
    ) {
        Optional<Product> foundProduct = productRepository.findById(id);

        return foundProduct.map(p -> ResponseEntity.ok(productMapper.toDto(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

}
