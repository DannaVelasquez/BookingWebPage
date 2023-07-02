package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
public class BookingResponseDTO {

    private Long id;

    private int people;

    private Double amount;

    private String registrationDate;

    private String startDate;

    private String endDate;

    private String comment;

    private Long userID;

    private Long experienceID;


    public static BookingResponseDTO toBookingResponseDTO(Booking bookingModel) {

        return BookingResponseDTO.builder()
                .id(bookingModel.getId())
                .people(bookingModel.getPeople())
                .amount(bookingModel.getAmount())
                .registrationDate(String.valueOf(bookingModel.getRegistrationDate()))
                .startDate(bookingModel.getStartDate())
                .endDate(bookingModel.getEndDate())
                .comment(bookingModel.getComment())
                .userID(bookingModel.getUser().getId())
                .experienceID(bookingModel.getExperience().getId())
                .build();

    }

    public static List<BookingResponseDTO> toBookingResponseDTOList(List<Booking> bookingList) {
        return bookingList.stream()
                .map(booking -> BookingResponseDTO.builder()
                        .id(booking.getId())
                        .people(booking.getPeople())
                        .amount(booking.getAmount())
                        .registrationDate(String.valueOf(booking.getRegistrationDate()))
                        .startDate(booking.getStartDate())
                        .endDate(booking.getEndDate())
                        .comment(booking.getComment())
                        .userID(booking.getUser().getId())
                        .experienceID(booking.getExperience().getId())
                        .build())
                .collect(Collectors.toList());
    }


}
