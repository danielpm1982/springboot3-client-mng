package com.danielpm1982.springboot3clientmng.error;
public class InvalidPayloadException extends RuntimeException{
    public InvalidPayloadException() {
        super();
    }
    public InvalidPayloadException(String message) {
        super(message);
    }
    public InvalidPayloadException(String message, Throwable cause) {
        super(message, cause);
    }
    public InvalidPayloadException(Throwable cause) {
        super(cause);
    }
    protected InvalidPayloadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
