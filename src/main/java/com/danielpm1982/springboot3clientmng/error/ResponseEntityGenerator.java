package com.danielpm1982.springboot3clientmng.error;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;

public class ResponseEntityGenerator {
    public static <T extends Response> ResponseEntity<T> mountResponseEntity(Class<T> responseType, HttpStatus httpStatus,
                                                                       String customResponseMessage, Exception e,
                                                                       HttpServletRequest request){
        Response response = null;
        try {
            response = T.getInstance(responseType);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e2) {
            throw new RuntimeException(e2);
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
        return new ResponseEntity<>((T)response,httpStatus);
    }
}
