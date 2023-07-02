package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.dto.request.validImage.ValidImageList;
import com.digitalhouse.digitalexpirience.model.expirience.Experience;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

@Builder
@Data
public class ExperienceDTO {
    //    todo - creamos validaciones con exprexiones regulares

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "Solo esta permitido caracteres Alfabeticos")
    private String title;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    private String description;

    @NotNull(message = "El campo requerido no puede ser nulo")
//    @ValidImageList(message = "URL inválida")
    private Collection<String> images;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @Min(value = 2, message = "La duración debe ser mayor a 1 día")
    private int durationInDays;

    @NotNull(message = "El campo requerido no puede ser nulo")
    private Double amountForPerson;

    @NotNull(message = "El campo requerido no puede ser nulo")
    private Long idPlace;

    private Long idCategory;

    private ArrayList<Long> attributes;

    public static Experience experienceDtoToExperienceModel(ExperienceDTO experienceDTO){
        return Experience.builder()
                .title(experienceDTO.getTitle())
                .description(experienceDTO.getDescription())
                .images(experienceDTO.getImages())
                .durationInDays(experienceDTO.getDurationInDays())
                .amountForPerson(experienceDTO.getAmountForPerson())
                .build();
    }
}
