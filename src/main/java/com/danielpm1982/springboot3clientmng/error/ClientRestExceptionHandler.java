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
public class ClientRestExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> clientNotFoundExceptionHandler(ClientNotFoundException e, HttpServletRequest request){
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse();
        clientErrorResponse.setTimestamp(ZonedDateTime.now());
        clientErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        clientErrorResponse.setError(e.getClass().getSimpleName());
        clientErrorResponse.setMessage(e.getMessage());
        clientErrorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> invalidPayloadExceptionHandler(InvalidPayloadException e, HttpServletRequest request){
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse();
        clientErrorResponse.setTimestamp(ZonedDateTime.now());
        clientErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        clientErrorResponse.setError(e.getClass().getSimpleName());
        clientErrorResponse.setMessage(e.getMessage());
        clientErrorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse();
        clientErrorResponse.setTimestamp(ZonedDateTime.now());
        clientErrorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        clientErrorResponse.setError(e.getClass().getSimpleName());
        clientErrorResponse.setMessage("Invalid clientId value type ! Please, send another request with the clientId as an int value.");
        clientErrorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e, HttpServletRequest request){
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse();
        clientErrorResponse.setTimestamp(ZonedDateTime.now());
        clientErrorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        clientErrorResponse.setError(e.getClass().getSimpleName());
        clientErrorResponse.setMessage("The requested endpoint does not exist. Please retry with a valid endpoint path !");
        clientErrorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> genericExceptionHandler(Exception e, HttpServletRequest request){
        ClientErrorResponse clientErrorResponse = new ClientErrorResponse();
        clientErrorResponse.setTimestamp(ZonedDateTime.now());
        clientErrorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        clientErrorResponse.setError(e.getClass().getSimpleName());
        clientErrorResponse.setMessage(e.getMessage());
        clientErrorResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler
    private ResponseEntity<ClientSuccessResponse> clientSuccessException(ClientSuccessException e, HttpServletRequest request){
        ClientSuccessResponse clientSuccessResponse = new ClientSuccessResponse();
        clientSuccessResponse.setTimestamp(ZonedDateTime.now());
        clientSuccessResponse.setStatus(HttpStatus.OK.value());
        clientSuccessResponse.setMessage(e.getMessage());
        clientSuccessResponse.setPath(request.getServletPath());
        return new ResponseEntity<>(clientSuccessResponse, HttpStatus.OK);
    }
}
