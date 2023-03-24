package com.danielpm1982.springboot3clientmng.error;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.lang.reflect.InvocationTargetException;
import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
public class Response {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private String path;
    public static <T extends Response> Response getInstance(Class<T> responseType) throws NoSuchMethodException,
            InvocationTargetException, InstantiationException, IllegalAccessException {
        Response response = responseType.getDeclaredConstructor().newInstance();
        return response;
    }
}
