package com.danielpm1982.springboot3clientmng.error;

public class ClientSuccessException extends RuntimeException{
    public ClientSuccessException(String message) {
        super(message);
    }
}
