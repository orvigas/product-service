package com.example.product.dtos;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.example.product.models.Product;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDto implements Serializable {

    private Long id;

    @Size(min = 2, max = 255)
    @NotBlank(message = "Sku is mandatory")
    private String sku;

    @JsonProperty("productName")
    @NotBlank(message = "productName is mandatory")
    private String name;

    @Size(min = 10, max = 255)
    @NotBlank(message = "description is mandatory")
    private String description;

    @NotNull(message = "price must be a decimal number")
    private double price;

    @NotNull(message = "taxRate must be a decimal number")
    private double taxRate;

    public Product model() {

        final var instance = new Product();
        BeanUtils.copyProperties(this, instance);

        return instance;
    }
}
