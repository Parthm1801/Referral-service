package com.example.referral_service.exception;

public class CustomerExistsException extends RuntimeException {
    private String message;

    public CustomerExistsException() {}

    public CustomerExistsException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
