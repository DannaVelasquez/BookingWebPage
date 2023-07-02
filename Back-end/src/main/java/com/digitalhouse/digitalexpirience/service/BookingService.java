package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.BookingDTO;
import com.digitalhouse.digitalexpirience.dto.response.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO bookingSave(String username, BookingDTO bookingDTO);


    List<BookingResponseDTO> getBooking(String username);

    void removeBooking(Long id);
}
