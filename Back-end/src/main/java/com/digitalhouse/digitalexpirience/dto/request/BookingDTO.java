package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class BookingDTO {


    private Integer people;

    private Double amount;

    private String startDate;

    private String endDate;

    private String comment;

    private Long experienceId;



    public static Booking toBookingModel(BookingDTO bookingDTO) {

        return Booking.builder()
                .people(bookingDTO.getPeople())
                .amount(bookingDTO.getAmount())
                .registrationDate(LocalDateTime.now())
                .startDate(bookingDTO.getStartDate())
                .endDate(bookingDTO.getEndDate())
                .comment(bookingDTO.getComment())
                .build();
    }
}
