package com.danielpm1982.springboot3clientmng.error;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data @NoArgsConstructor
public class ErrorResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
