package com.danielpm1982.springboot3clientmng.error;

public class InvalidPayloadException extends RuntimeException{
    public InvalidPayloadException(String message) {
        super(message);
    }
}
