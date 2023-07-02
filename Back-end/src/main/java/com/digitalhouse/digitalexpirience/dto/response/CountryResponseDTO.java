package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.place.Country;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class CountryResponseDTO {
    private Long id;

    private String name;

//    private Float latitude;
//
//    private Float longitude;

    private List<CityResponseDTO> cities;

    public static CountryResponseDTO countryModelToCountryDto(Country country){
        return CountryResponseDTO.builder()
                .id(country.getId())
                .name(country.getName())
//                .latitude(country.getLatitude())
//                .longitude(country.getLongitude())
                .cities(CityResponseDTO.cityMoldesToResponseDTOs(new ArrayList<>(country.getCities())))
                .build();
    }

    public static List<CountryResponseDTO> countryModelToCountryList(List<Country> listCountries) {
        if(listCountries == null){
            return null;
        }
        List<CountryResponseDTO> listCountriesDTO = new ArrayList<>();
        for (Country country: listCountries) {
            listCountriesDTO.add(countryModelToCountryDto(country));
        }
        return listCountriesDTO;
    }

    public static List<CountryResponseDTO> countryModelsToCoutryDTOs (List<Country> countryList){
        return countryList.stream().map(mapper -> CountryResponseDTO.builder()
                .id(mapper.getId())
                .name(mapper.getName())
//                .latitude(mapper.getLatitude())
//                .longitude(mapper.getLongitude())
                .cities(CityResponseDTO.cityMoldesToResponseDTOs(new ArrayList<>(mapper.getCities())))
                .build()).collect(Collectors.toList());
    }
}
