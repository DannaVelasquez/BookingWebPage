package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.expirience.Category;
import com.digitalhouse.digitalexpirience.model.place.City;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;

@Data
@Builder
public class CityDTO {

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    private String name;

    private List<Long> places;

    public static City cityDtoToCityModel(CityDTO cityDTO){
        return City.builder()
                .name(cityDTO.getName())
                .build();
    }
}
