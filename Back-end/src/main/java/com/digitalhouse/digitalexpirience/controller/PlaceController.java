package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    PlaceService placeService;

    @PostMapping("/create")
    public ResponseEntity<Object> placeControllerSave(@Valid @RequestBody PlaceDTO placeDTO) {
        var placeResponseDTO = placeService.placeSave(placeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(placeResponseDTO, "Success", HttpStatus.CREATED));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> placeControllerRead(@PathVariable Long id){
        var placeResponseDTO = placeService.getPlaceById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(placeResponseDTO, "Success", HttpStatus.OK));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> placeControllerDelete(@PathVariable Long id) {
        placeService.placeDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> placeControllerUpdate(@PathVariable Long id,@Valid @RequestBody PlaceDTO placeDTO) {
        var placeResponseDTO = placeService.placeUpdate(id, placeDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(placeResponseDTO, "Success", HttpStatus.OK));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Object> placeControllerGetAll(){
        var placeCitiesDTO = placeService.getAllPlace();
        return ResponseEntity.ok().body(ApiResponseDTO.created(placeCitiesDTO, "Success", HttpStatus.OK));
    }
}