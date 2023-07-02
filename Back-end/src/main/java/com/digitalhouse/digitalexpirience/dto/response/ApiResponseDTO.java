package com.digitalhouse.digitalexpirience.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO<T> implements Serializable {

    private T data;
    private String message;
    private HttpStatus status;
    private int statusCode;

    public static <T> ApiResponseDTO<T> created(T data, String message, HttpStatus status) {
        return new ApiResponseDTO<T>(data, message, status, status.value());
    }

    public static <T> ApiResponseDTO<T> created(String message, HttpStatus status) {
        return new ApiResponseDTO<T>(null, message, status, status.value());
    }
}
