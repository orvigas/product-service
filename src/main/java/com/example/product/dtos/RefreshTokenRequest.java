package com.example.product.dtos;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest implements Serializable {
    @JsonProperty("refresh_token")
    @NotBlank(message = "The \"refresh_token\" field is mandatory")
    private UUID refreshToken;
}
