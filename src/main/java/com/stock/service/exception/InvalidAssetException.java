package com.stock.service.exception;

public class InvalidAssetException extends RuntimeException {
    public InvalidAssetException(String message) {
        super(message);
    }
}
