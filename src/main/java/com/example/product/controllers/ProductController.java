package com.example.product.controllers;

import com.example.product.dtos.ProductDto;
import com.example.product.models.Product;
import com.example.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@Validated
@RequestMapping(path = "/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Page<ProductDto>> getProducts(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "ASC") String direction) {
        return ResponseEntity.ok(productService
                .all(PageRequest.of(pageNumber, pageSize, Sort.by(Direction.fromString(direction), sort)))
                .map(Product::dto));
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ProductDto> read(@PathVariable(required = true) long id) {
        return ResponseEntity.ok(productService.get(id).dto());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductDto> create(@RequestBody(required = true) @NonNull @Valid ProductDto productDto) {
        return ResponseEntity.ok(productService.save(productService.save(productDto.model())).dto());
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<ProductDto> update(@PathVariable(required = true) long id,
            @RequestBody(required = true) ProductDto productDto) {
        return ResponseEntity.ok(productService.update(id, productDto.model()).dto());
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable(required = true) long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
