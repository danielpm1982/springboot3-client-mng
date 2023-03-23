package com.danielpm1982.springboot3clientmng.error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> clientNotFoundExceptionHandler(ClientNotFoundException e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> clientNotFoundExceptionHandler(AddressNotFoundException e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> invalidPayloadExceptionHandler(InvalidPayloadException e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage("Invalid clientId value type ! Please, send another request with the clientId as an int value.");
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage("The requested endpoint does not exist. Please retry with a valid endpoint path !");
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> genericExceptionHandler(Exception e, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(ZonedDateTime.now());
        errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setError(e.getClass().getSimpleName());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> clientSuccessException(ClientSuccessException e, HttpServletRequest request){
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setTimestamp(ZonedDateTime.now());
        successResponse.setStatus(HttpStatus.OK.value());
        successResponse.setMessage(e.getMessage());
        successResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> addressSuccessException(AddressSuccessException e, HttpServletRequest request){
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setTimestamp(ZonedDateTime.now());
        successResponse.setStatus(HttpStatus.OK.value());
        successResponse.setMessage(e.getMessage());
        successResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
