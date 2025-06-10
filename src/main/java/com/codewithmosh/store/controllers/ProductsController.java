package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.product.ProductDto;
import com.codewithmosh.store.entities.Category;
import com.codewithmosh.store.entities.Product;
import com.codewithmosh.store.mappers.ProductMapper;
import com.codewithmosh.store.repositories.CategoryRepository;
import com.codewithmosh.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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
            @PathVariable(name = "id") Long id
    ) {
        Optional<Product> foundProduct = productRepository.findById(id);

        return foundProduct.map(p -> ResponseEntity.ok(productMapper.toDto(p)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto,
            UriComponentsBuilder uriBuilder
    ) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Product product = productMapper.toEntity(productDto);
        product.setCategory(category);
        productRepository.save(product);
        productDto.setId(product.getId());

        URI uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();

        return ResponseEntity
                .created(uri)
                .body(productDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable(name = "id") Long id,
            @RequestBody ProductDto productDto
    ) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
        if (category == null) {
            return ResponseEntity.badRequest().build();
        }
        Product foundProduct = productRepository.findById(id).orElse(null);
        if (foundProduct == null) {
            return ResponseEntity.notFound().build();
        }
        productMapper.update(productDto, foundProduct);
        foundProduct.setCategory(category);
        productRepository.save(foundProduct);
        productDto.setId(foundProduct.getId());

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "id") Long id
    ) {
        Product foundProduct = productRepository.findById(id).orElse(null);
        if (foundProduct == null) {
            return ResponseEntity.notFound().build();
        }
        productRepository.delete(foundProduct);

        return ResponseEntity.noContent().build();
    }

}
