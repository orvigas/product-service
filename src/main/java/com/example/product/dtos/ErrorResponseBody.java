package com.example.product.dtos;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseBody implements Serializable {

    private int code;
    private HttpStatus status;
    private String msg;
    private Instant time;

    public Instant getTime() {
        if (this.time == null) {
            this.time = Instant.now();
        }

        return this.time;
    }
}
