package com.example.product.services;

import com.example.product.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<Product> all() {
        return List.of(new Product());
    }

    public Product get(final long id) {
        return new Product();
    }

    public Product save(final Product product) {
        return new Product();
    }

    public Product update(final long id, final Product product) {
        return new Product();
    }

    public void delete(final long id) {

    }
}
