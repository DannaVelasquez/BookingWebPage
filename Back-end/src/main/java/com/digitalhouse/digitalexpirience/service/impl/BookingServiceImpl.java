package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.BookingDTO;
import com.digitalhouse.digitalexpirience.dto.response.BookingResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BadRequestException;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.repository.BookingRepository;
import com.digitalhouse.digitalexpirience.repository.ExperienceRepository;
import com.digitalhouse.digitalexpirience.repository.UserRepository;
import com.digitalhouse.digitalexpirience.service.BookingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BookingServiceImpl implements BookingService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Override
    public BookingResponseDTO bookingSave(String username, BookingDTO bookingDTO) {

        var userOp = userRepository.findByUserName(username);

        if (userOp.isPresent()) {
            var userModel = userOp.get();
            var experienceOp = experienceRepository.findById(bookingDTO.getExperienceId());

            if (experienceOp.isPresent()) {
                var experienceModel = experienceOp.get();
                var bookingModel = BookingDTO.toBookingModel(bookingDTO);

                bookingModel.setUser(userModel);
                bookingModel.setExperience(experienceModel);

                bookingRepository.save(bookingModel);
                return BookingResponseDTO.toBookingResponseDTO(bookingModel);
            } else {
                throw new BadRequestException("La Experiencia no esta presente");
            }

        } else {
            throw new BadRequestException("El usuario no esta presente");
        }
    }


    @Override
    public List<BookingResponseDTO> getBooking(String username) {

        var userOp = userRepository.findByUserName(username);

        if (userOp.isPresent()) {
            var userModel = userOp.get();
            var bookingList = bookingRepository.findBookingByUserId(userModel.getId());

            return BookingResponseDTO.toBookingResponseDTOList(bookingList);

        }else{

            throw new BadRequestException("El usuario no tiene reservas");
        }

    }

    @Override
    @SneakyThrows
    public void removeBooking(Long id) {
        try {
            var isBookingExist = bookingRepository.findById(id);
            if (isBookingExist.isPresent()){
                var bookingModel = isBookingExist.get();
                bookingModel.deleted();
                bookingRepository.save(bookingModel);
            } else {
                throw new BadRequestException("El id de la reserva no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }


}
