package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.BookingTimeIntervalDTO;
import com.digitalhouse.digitalexpirience.dto.request.ExperienceDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.ExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/experiences")
public class ExperienceController {
    @Autowired
    ExperienceService experienceService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> experienceControllerSave(@Valid @RequestBody ExperienceDTO experienceDTO) {
        var experienceResponseDTO = experienceService.experienceSave(experienceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(experienceResponseDTO, "Success", HttpStatus.CREATED));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> experienceControllerRead(@PathVariable Long id){
        var experienceResponseDTO = experienceService.getExperienceById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(experienceResponseDTO, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> userControllerUpdate(@PathVariable Long id, @Valid @RequestBody ExperienceDTO experienceDTO) {
        var experienceResponseDTO = experienceService.updateExperience(id, experienceDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(experienceResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> experienceControllerDelete(@PathVariable Long id) {
        experienceService.experienceDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @GetMapping("/recommend")
    public ResponseEntity<Object> experienceControllerRecommend(){
        var list = experienceService.experienceRecommendList();
        return ResponseEntity.ok().body(ApiResponseDTO.created(list, "Success", HttpStatus.OK));

    }

    @PutMapping("/update-category/idExpirience={idExpirience}/idCategory={idCategory}")
    public ResponseEntity<Object> updateCategoryToExpirience(@PathVariable Long idExpirience, @PathVariable  Long idCategory){
        var experienceResponseDTO = experienceService.experienceUpdateCategory(idExpirience,idCategory);
        return ResponseEntity.ok().body(ApiResponseDTO.created(experienceResponseDTO, "Success", HttpStatus.OK));
    }

    @GetMapping("/search-by-category/{id}")
    public ResponseEntity<Object> getExperienceByCategory(@PathVariable Long id){
        var listExperienceResponseDTO = experienceService.experienceByCategoryList(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(listExperienceResponseDTO, "Success", HttpStatus.OK));
    }

    @GetMapping("/search-by-city/{id}")
    public ResponseEntity<Object> getExperienceByCity(@PathVariable Long id){
        var listExperienceResponseDTO = experienceService.experienceByCityList(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(listExperienceResponseDTO, "Success", HttpStatus.OK));
    }

    @GetMapping("/get-booking/{id}")
    public ResponseEntity<Object> getBookingForExperience(@PathVariable Long id){
        var listBookingResponseDTO = experienceService.bookingByExperience(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(listBookingResponseDTO, "Success", HttpStatus.OK));
    }

    @PostMapping("/search")
    public ResponseEntity<Object> getExperienceByCityAndTime(@RequestBody BookingTimeIntervalDTO bookingTimeIntervalDTO){
        var listExperienceResponseDTO = experienceService.experienceByCityAndTimeList(bookingTimeIntervalDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(listExperienceResponseDTO, "Success", HttpStatus.OK));
    }

}
