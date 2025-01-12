package com.example.product.services;

import com.example.product.models.Product;
import com.example.product.repositories.ProductRepository;
import lombok.Data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Data
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Page<Product> all(final PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }

    public Product get(final long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product save(final Product product) {
        return productRepository.save(product);
    }

    public Product update(final long id, final Product product) {
        return productRepository.findById(id).map(existing->{
            product.setId(existing.getId());
            return productRepository.save(product);
        }).orElseThrow();
    }

    public void delete(final long id) {
        productRepository.deleteById(id);
    }
}
