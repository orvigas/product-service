package com.example.product.models;

import java.math.BigInteger;

import org.springframework.beans.BeanUtils;

import com.example.product.dtos.ProductDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sku")
    private String sku;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private double price;
    @Column(name = "tax_rate")
    private double taxRate;

    public ProductDto dto() {

        final var instance = new ProductDto();
        BeanUtils.copyProperties(this, instance);

        return instance;
    }
}
