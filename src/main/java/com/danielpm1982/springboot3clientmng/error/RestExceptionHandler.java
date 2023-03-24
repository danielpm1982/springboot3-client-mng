package com.danielpm1982.springboot3clientmng.error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> clientNotFoundExceptionHandler(ClientNotFoundException e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> addressNotFoundExceptionHandler(AddressNotFoundException e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> invalidPayloadExceptionHandler(InvalidPayloadException e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.BAD_REQUEST, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        String customMessage = "Invalid clientId value type ! Please, send another request with the clientId as an int value.";
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.BAD_REQUEST, customMessage, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e, HttpServletRequest request){
        String customMessage = "The requested endpoint does not exist. Please retry with a valid endpoint path !";
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, customMessage, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> genericExceptionHandler(Exception e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(ErrorResponse.class, HttpStatus.INTERNAL_SERVER_ERROR, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> clientSuccessException(ClientSuccessException e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(SuccessResponse.class, HttpStatus.OK, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> addressSuccessException(AddressSuccessException e, HttpServletRequest request){
        return ResponseEntityGenerator.mountResponseEntity(SuccessResponse.class, HttpStatus.OK, null, e, request);
    }
}
