package com.digitalhouse.digitalexpirience.dto.response;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class APIErrorDTO<T> extends ApiResponseDTO<T> {

    private List<String> errors;

    @Builder(builderMethodName = "Builder")
    public APIErrorDTO(T data, String message, HttpStatus status, List<String> errors) {
        super(data, message, status, status.value());
        this.errors = errors;
    }

    public static <T> APIErrorDTO<T> created(T data, String message, HttpStatus status, List<String> errors) {

        return new APIErrorDTO<T>(data, message, status, errors);

    }

    public static <T> APIErrorDTO<T> created(String message, HttpStatus status, List<String> errors) {

        return new APIErrorDTO<T>(null, message, status, errors);

    }


}
