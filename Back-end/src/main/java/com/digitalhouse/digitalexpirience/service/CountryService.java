package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.CountryDTO;
import com.digitalhouse.digitalexpirience.dto.response.CountryResponseDTO;
import java.util.List;

public interface CountryService {
    CountryResponseDTO countrySave(CountryDTO countryDTO);

    CountryResponseDTO getCountryById(Long id);

    void countryDelete(Long id);

    CountryResponseDTO countryUpdate(Long id, CountryDTO countryDTO);

    List<CountryResponseDTO> getAllCountries();

    CountryResponseDTO countryAddCity(Long id, CityDTO cityDTO);

    CountryResponseDTO countryRemoveCity(Long id, CityDTO cityDTO);
}
