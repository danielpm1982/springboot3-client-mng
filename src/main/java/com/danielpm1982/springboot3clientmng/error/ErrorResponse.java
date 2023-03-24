package com.danielpm1982.springboot3clientmng.error;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class ErrorResponse extends Response{
    private String error;
}
