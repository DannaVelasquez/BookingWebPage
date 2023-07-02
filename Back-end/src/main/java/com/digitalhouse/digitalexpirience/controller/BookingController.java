package com.digitalhouse.digitalexpirience.controller;


import com.digitalhouse.digitalexpirience.dto.request.BookingDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping("/save-booking")
    public ResponseEntity<Object> experienceBookingSave(@Valid @RequestBody BookingDTO bookingDTO,
                                                        @AuthenticationPrincipal UserDetails userDetails) {
        var username = userDetails.getUsername();
        var experienceBookingResponseDTO = bookingService.bookingSave(username, bookingDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(experienceBookingResponseDTO, "Success", HttpStatus.CREATED));
    }


    @GetMapping("/get-booking")
    public ResponseEntity<Object> getExperienceBooking(@AuthenticationPrincipal UserDetails userDetails) {
        var username = userDetails.getUsername();
        var experienceBookingResponseDTO = bookingService.getBooking(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(experienceBookingResponseDTO, "Success", HttpStatus.CREATED));
    }

    @DeleteMapping("/remove/{id}")
    public  ResponseEntity<Object> removeBooking(@PathVariable Long id){
        bookingService.removeBooking(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(null, "Success", HttpStatus.CREATED));
    }
}
