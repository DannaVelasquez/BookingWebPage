package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingTimeIntervalResponseDTO {
    public Long id;

    public String dayStart;

    public String dayEnd;

    public static BookingTimeIntervalResponseDTO bookingModelToBookingTimeIntervalResponseDTO(Booking booking){
        return BookingTimeIntervalResponseDTO.builder()
                .id(booking.getId())
                .dayStart(booking.getStartDate())
                .dayEnd(booking.getEndDate())
                .build();
    }

    public static List<BookingTimeIntervalResponseDTO> toBookingTimeIntervalResponseDTOList(List<Booking> bookingList){
        return bookingList.stream()
                .map(booking -> BookingTimeIntervalResponseDTO.builder()
                        .id(booking.getId())
                        .dayStart(booking.getStartDate())
                        .dayEnd(booking.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }

}
