package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.place.Place;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PlaceDTO {

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    private String name;

//    @NotNull(message = "El campo requerido no puede ser nulo")
//    private Float latitude;
//
//    @NotNull(message = "El campo requerido no puede ser nulo")
//    private Float longitude;

    @NotNull(message = "El campo requerido no puede ser nulo")
    private Long idCountry;

    @NotNull(message = "El campo requerido no puede ser nulo")
    private Long idCity;

    public static Place placeDtoToPlaceModel(PlaceDTO placeDTO){
        return Place.builder()
                .name(placeDTO.getName())
                .build();
    }

//    public static PlaceDTO placeModelToPlaceDTO(Place place){
//        return PlaceDTO.builder()
//                .name()
//    }
}
