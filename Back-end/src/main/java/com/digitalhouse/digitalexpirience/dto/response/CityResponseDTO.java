package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.place.City;
import com.digitalhouse.digitalexpirience.model.place.Place;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class CityResponseDTO {
    private Long id;

    private String name;

//    private Float latitude;

//    private Float longitude;

    private List<PlaceResponseDTO> places;

    public static CityResponseDTO cityModelToCityDto(City city){
        return CityResponseDTO.builder()
                .id(city.getId())
                .name(city.getName())
//                .latitude(city.getLatitude())
//                .longitude(city.getLongitude())
                .places(PlaceResponseDTO.placeModelsToPlaceResponseDtos(new ArrayList<>(city.getPlaces())))
                .build();
    }

    public static List<CityResponseDTO> cityModelToCityList(List<City> cityList) {
        if(cityList == null){
            return null;
        }
        List<CityResponseDTO> listCitiesDTO = new ArrayList<>();
        for (City city: cityList) {
            listCitiesDTO.add(cityModelToCityDto(city));
        }
        return listCitiesDTO;
    }

    public static List<CityResponseDTO> cityMoldesToResponseDTOs(List<City> cityList){
        return cityList.stream().map(mapper -> CityResponseDTO.builder()
                .id(mapper.getId())
                .name(mapper.getName())
//                .latitude(mapper.getLatitude())
//                .longitude(mapper.getLongitude())
                .places(PlaceResponseDTO.placeModelsToPlaceResponseDtos(new ArrayList<>(mapper.getPlaces())))
                .build()).collect(Collectors.toList());
    }
}
