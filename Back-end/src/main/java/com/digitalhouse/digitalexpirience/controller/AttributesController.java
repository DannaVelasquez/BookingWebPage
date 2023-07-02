package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.AttributeDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.AttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/attributes")
public class AttributesController {
    @Autowired
    AttributeService attributeService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> attributeControllerSave(@Valid @RequestBody AttributeDTO attributeDTO) {
        var attributeResponseDTO = attributeService.attributeSave(attributeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(attributeResponseDTO, "Success", HttpStatus.CREATED));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> attributeControllerRead(@PathVariable Long id){
        var attributeResponseDTO = attributeService.getAttributeById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(attributeResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> attributeControllerDelete(@PathVariable Long id) {
        attributeService.attributeDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> attributeControllerUpdate(@PathVariable Long id,@Valid @RequestBody AttributeDTO attributeDTO) {
        var attributeResponseDTO = attributeService.attributeUpdate(id, attributeDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(attributeResponseDTO, "Success", HttpStatus.OK));
    }
    @GetMapping("/get-all")
    public ResponseEntity<Object> attributeControllerGetAll(){
        var listAttributeDTO = attributeService.getAllAttributes();
        return ResponseEntity.ok().body(ApiResponseDTO.created(listAttributeDTO, "Success", HttpStatus.OK));
    }
}
