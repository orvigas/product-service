package com.example.product.dtos;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest implements Serializable {

    @NotBlank(message = "The \"username\" field is mandatory")
    private String username;
    @NotBlank(message = "The \"password\" field is mandatory")
    private String password;
}
