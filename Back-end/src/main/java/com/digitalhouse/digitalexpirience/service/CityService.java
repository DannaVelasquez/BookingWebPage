package com.digitalhouse.digitalexpirience.service;


import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.CityResponseDTO;
import com.digitalhouse.digitalexpirience.model.place.Place;

import java.util.List;
import java.util.Set;

public interface CityService {
    CityResponseDTO citySave(CityDTO cityDTO);

    CityResponseDTO getCityById(Long id);

    void cityDelete(Long id);

    CityResponseDTO cityUpdate(Long id, CityDTO cityDTO);

    List<CityResponseDTO> getAllCities();

    CityResponseDTO cityAddPlace(Long id, PlaceDTO placeDTO);

    CityResponseDTO cityAddPlace(Long id, Place place);

    CityResponseDTO cityRemovePlace(Long id, PlaceDTO placeDTO);
}
