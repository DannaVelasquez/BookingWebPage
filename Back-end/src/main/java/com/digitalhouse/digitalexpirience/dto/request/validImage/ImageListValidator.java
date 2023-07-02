package com.digitalhouse.digitalexpirience.dto.request.validImage;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;
import java.util.regex.Pattern;

public class ImageListValidator implements ConstraintValidator<ValidImageList, Collection<String>> {
    private static final String URL_PATTERN = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";

    @Override
    public void initialize(ValidImageList constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<String> images, ConstraintValidatorContext context) {
        if (images == null || images.isEmpty()) {
            return true;  // Permite una lista vacía, puedes cambiarlo si es necesario
        }

        Pattern pattern = Pattern.compile(URL_PATTERN);
        for (String image : images) {
            if (image == null || image.isBlank() || !pattern.matcher(image).matches()) {
                return false;  // Invalida la colección si algún elemento no coincide con el patrón
            }
        }

        return true;  // La colección pasa la validación
    }
}