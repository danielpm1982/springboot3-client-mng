package com.danielpm1982.springboot3clientmng.error;

public class AddressSuccessException extends RuntimeException{
    public AddressSuccessException() {
        super();
    }
    public AddressSuccessException(String message) {
        super(message);
    }
    public AddressSuccessException(String message, Throwable cause) {
        super(message, cause);
    }
    public AddressSuccessException(Throwable cause) {
        super(cause);
    }
    protected AddressSuccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
