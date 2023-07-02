package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.PlaceResponseDTO;

import java.util.List;

public interface PlaceService {
    PlaceResponseDTO placeSave(PlaceDTO placeDTO);

    PlaceResponseDTO getPlaceById(Long id);

    void placeDelete(Long id);

    PlaceResponseDTO placeUpdate(Long id, PlaceDTO placeDTO);

    List<PlaceResponseDTO> getAllPlace();

}
