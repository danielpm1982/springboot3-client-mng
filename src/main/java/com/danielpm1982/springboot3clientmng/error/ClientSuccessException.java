package com.danielpm1982.springboot3clientmng.error;

public class ClientSuccessException extends RuntimeException{
    public ClientSuccessException() {
        super();
    }
    public ClientSuccessException(String message) {
        super(message);
    }
    public ClientSuccessException(String message, Throwable cause) {
        super(message, cause);
    }
    public ClientSuccessException(Throwable cause) {
        super(cause);
    }
    protected ClientSuccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
