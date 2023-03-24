package com.danielpm1982.springboot3clientmng.error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> clientNotFoundExceptionHandler(ClientNotFoundException e, HttpServletRequest request){
        return mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> addressNotFoundExceptionHandler(AddressNotFoundException e, HttpServletRequest request){
        return mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> invalidPayloadExceptionHandler(InvalidPayloadException e, HttpServletRequest request){
        return mountResponseEntity(ErrorResponse.class, HttpStatus.BAD_REQUEST, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request){
        String customMessage = "Invalid clientId value type ! Please, send another request with the clientId as an int value.";
        return mountResponseEntity(ErrorResponse.class, HttpStatus.BAD_REQUEST, customMessage, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> noHandlerFoundExceptionHandler(NoHandlerFoundException e, HttpServletRequest request){
        String customMessage = "The requested endpoint does not exist. Please retry with a valid endpoint path !";
        return mountResponseEntity(ErrorResponse.class, HttpStatus.NOT_FOUND, customMessage, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<ErrorResponse> genericExceptionHandler(Exception e, HttpServletRequest request){
        return mountResponseEntity(ErrorResponse.class, HttpStatus.INTERNAL_SERVER_ERROR, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> clientSuccessException(ClientSuccessException e, HttpServletRequest request){
        return mountResponseEntity(SuccessResponse.class, HttpStatus.OK, null, e, request);
    }
    @ExceptionHandler
    private ResponseEntity<SuccessResponse> addressSuccessException(AddressSuccessException e, HttpServletRequest request){
        return mountResponseEntity(SuccessResponse.class, HttpStatus.OK, null, e, request);
    }
    private <T extends Response> ResponseEntity<T> mountResponseEntity(Class<T> responseType, HttpStatus httpStatus,
                                                                       String customResponseMessage, Exception e,
                                                                       HttpServletRequest request){
        Response response = null;
        try {
            response = T.getInstance(responseType);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
        response.setTimestamp(ZonedDateTime.now());
        response.setStatus(httpStatus.value());
        if(response instanceof ErrorResponse){
           ((ErrorResponse) response).setError(e.getClass().getSimpleName());
        }
        if(customResponseMessage!=null){
            response.setMessage(customResponseMessage);
        } else{
            response.setMessage(e.getMessage());
        }
        response.setPath(request.getServletPath());
        return new ResponseEntity<T>((T)response,httpStatus);
    }
}
