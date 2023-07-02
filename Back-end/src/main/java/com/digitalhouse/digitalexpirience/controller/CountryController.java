package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.AttributeDTO;
import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.CountryDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    CountryService countryService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> countryControllerSave(@Valid @RequestBody CountryDTO countryDTO) {
        var countryResponseDTO = countryService.countrySave(countryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(countryResponseDTO, "Success", HttpStatus.CREATED));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> countryControllerRead(@PathVariable Long id){
        var countryResponseDTO = countryService.getCountryById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(countryResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> countryControllerDelete(@PathVariable Long id) {
        countryService.countryDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> countryControllerUpdate(@PathVariable Long id,@Valid @RequestBody CountryDTO countryDTO) {
        var countryResponseDTO = countryService.countryUpdate(id, countryDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(countryResponseDTO, "Success", HttpStatus.OK));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Object> countryControllerGetAll(){
        var listCountryDTO = countryService.getAllCountries();
        return ResponseEntity.ok().body(ApiResponseDTO.created(listCountryDTO, "Success", HttpStatus.OK));
    }

    @PostMapping("/add-city/{id}")
    public ResponseEntity<Object> countryControllerAddCity(@PathVariable Long id,@Valid @RequestBody CityDTO cityDTO){
        var countryResponseDTO = countryService.countryAddCity(id, cityDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(countryResponseDTO, "Success", HttpStatus.OK));
    }

    @PostMapping("/remove-city/{id}")
    public ResponseEntity<Object> countryControllerRemoveCity(@PathVariable Long id,@Valid @RequestBody CityDTO cityDTO){
        var countryResponseDTO = countryService.countryRemoveCity(id, cityDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(countryResponseDTO, "Success", HttpStatus.OK));
    }
}
