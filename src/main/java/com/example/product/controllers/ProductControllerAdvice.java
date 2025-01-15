package com.example.product.controllers;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.product.dtos.ErrorResponseBody;

@RestControllerAdvice
public class ProductControllerAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ErrorResponseBody> notFoundException(final NoSuchElementException e) {
        return new ResponseEntity<>(ErrorResponseBody.builder().code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND).msg("HTTP 404 Not Found").build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<ErrorResponseBody> badREquest(final MethodArgumentNotValidException e) {
        final var msg = String.join(", ", e.getAllErrors().stream().map(ObjectError::getDefaultMessage).toList());
        return new ResponseEntity<>(ErrorResponseBody.builder().code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST).msg(msg).build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ErrorResponseBody> serverError(final Throwable e) {
        return new ResponseEntity<>(ErrorResponseBody.builder().code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR).msg(e.getLocalizedMessage()).build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
