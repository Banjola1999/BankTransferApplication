package com.example.banktransferapplication.exception;
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super("Account Not Found");
    }
}
