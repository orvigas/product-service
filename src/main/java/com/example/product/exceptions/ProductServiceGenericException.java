package com.example.product.exceptions;

public class ProductServiceGenericException extends RuntimeException {

    public ProductServiceGenericException() {
        super();
    }

    public ProductServiceGenericException(final Throwable error) {
        super(error);
    }

    public ProductServiceGenericException(final String message) {
        super(message);
    }

    public ProductServiceGenericException(final String message, final Throwable error) {
        super(message, error);
    }

}
