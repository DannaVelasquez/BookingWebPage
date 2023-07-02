package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.AttributeDTO;
import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    CityService cityService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> cityControllerSave(@Valid @RequestBody CityDTO cityDTO) {
        var cityResponseDTO = cityService.citySave(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(cityResponseDTO, "Success", HttpStatus.CREATED));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> cityControllerRead(@PathVariable Long id){
        var cityResponseDTO = cityService.getCityById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(cityResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> cityControllerDelete(@PathVariable Long id) {
        cityService.cityDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> cityControllerUpdate(@PathVariable Long id,@Valid @RequestBody CityDTO cityDTO) {
        var cityResponseDTO = cityService.cityUpdate(id, cityDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(cityResponseDTO, "Success", HttpStatus.OK));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Object> cityControllerGetAll(){
        var listCitiesDTO = cityService.getAllCities();
        return ResponseEntity.ok().body(ApiResponseDTO.created(listCitiesDTO, "Success", HttpStatus.OK));
    }

    @PostMapping("/add-place/{id}")
    public ResponseEntity<Object> countryControllerAddCity(@PathVariable Long id,@Valid @RequestBody PlaceDTO placeDTO){
        var cityResponseDTO = cityService.cityAddPlace(id, placeDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(cityResponseDTO, "Success", HttpStatus.OK));
    }

    @PostMapping("/remove-place/{id}")
    public ResponseEntity<Object> countryControllerRemoveCity(@PathVariable Long id,@Valid @RequestBody PlaceDTO placeDTO){
        var cityResponseDTO = cityService.cityRemovePlace(id, placeDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(cityResponseDTO, "Success", HttpStatus.OK));
    }
}
