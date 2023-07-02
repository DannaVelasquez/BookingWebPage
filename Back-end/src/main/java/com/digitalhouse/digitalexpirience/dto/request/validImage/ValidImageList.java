package com.digitalhouse.digitalexpirience.dto.request.validImage;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = ImageListValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImageList {
    String message() default "Lista de imágenes inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}