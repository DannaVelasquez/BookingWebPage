package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.place.City;
import com.digitalhouse.digitalexpirience.model.place.Country;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Data
public class CountryDTO {
    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    private String name;

    private List<Long> cities;

    public static Country countryDtoToCountryModel(CountryDTO countryDTO){
        return Country.builder()
                .name(countryDTO.getName())
                .build();
    }
}
