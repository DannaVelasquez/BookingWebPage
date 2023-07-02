package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class BookingTimeIntervalDTO {
    public Long idCity;

    public String startDay;

    public String endDay;

    public static BookingTimeIntervalDTO bookingModelToBookingTimeIntervalResponseDTO(Booking booking){
        return BookingTimeIntervalDTO.builder()
                .idCity(booking.getId())
                .startDay(booking.getStartDate())
                .endDay(booking.getEndDate())
                .build();
    }

    public static List<BookingTimeIntervalDTO> toBookingTimeIntervalResponseDTOList(List<Booking> bookingList){
        return bookingList.stream()
                .map(booking -> BookingTimeIntervalDTO.builder()
                        .idCity(booking.getId())
                        .startDay(booking.getStartDate())
                        .endDay(booking.getEndDate())
                        .build())
                .collect(Collectors.toList());
    }
}
