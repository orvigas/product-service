package com.example.product.controllers;

import com.example.product.models.Product;
import com.example.product.services.ProductService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Data
@RestController
@Validated
@RequestMapping(path = "/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/list")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(productService.all());
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Product> read(@PathVariable(required = true) long id) {
        return ResponseEntity.ok(productService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> create(@RequestBody(required = true) @NonNull @Valid Product product) {
        return ResponseEntity.ok(productService.save(productService.save(product)));
    }

    @PutMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Product> update(@PathVariable(required = true) long id, @RequestBody(required = true) Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@PathVariable(required = true) long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
