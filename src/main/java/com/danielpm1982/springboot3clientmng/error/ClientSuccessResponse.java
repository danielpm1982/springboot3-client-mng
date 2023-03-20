package com.danielpm1982.springboot3clientmng.error;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;

@Data @NoArgsConstructor
public class ClientSuccessResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String message;
    private String path;
}
