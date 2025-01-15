package com.example.product.services;

import com.example.product.exceptions.ProductServiceGenericException;
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
        try {
            return productRepository.findAll(pageRequest);
        } catch (Throwable e) {
            throw new ProductServiceGenericException(e.getClass() + "  in service layer", e);
        }
    }

    public Product get(final long id) {
        try {
            return productRepository.findById(id).orElseThrow();
        } catch (Throwable e) {
            throw new ProductServiceGenericException(e.getClass() + "  in service layer", e);
        }
    }

    public Product save(final Product product) {
        try {
            return productRepository.save(product);
        } catch (Throwable e) {
            throw new ProductServiceGenericException(e.getClass() + "  in service layer", e);
        }
    }

    public Product update(final long id, final Product product) {
        try {
            return productRepository.findById(id).map(existing -> {
                product.setId(existing.getId());
                return this.save(product);
            }).orElseThrow();
        } catch (Throwable e) {
            throw new ProductServiceGenericException(e.getClass() + "  in service layer", e);
        }
    }

    public void delete(final long id) {
        try {
            productRepository.deleteById(id);
        } catch (Throwable e) {
            throw new ProductServiceGenericException(e.getClass() + "  in service layer", e);
        }
    }
}
