package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/politics")
public class PoliticController {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping("/get-all")
    public ResponseEntity<Object> politicControllerGetAll(){

        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

}
