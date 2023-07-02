package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.BookingTimeIntervalDTO;
import com.digitalhouse.digitalexpirience.dto.request.ExperienceDTO;
import com.digitalhouse.digitalexpirience.dto.response.*;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.model.Reserve;
import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import com.digitalhouse.digitalexpirience.model.expirience.Experience;
import com.digitalhouse.digitalexpirience.repository.*;
import com.digitalhouse.digitalexpirience.service.ExperienceService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    AttributeServiceImpl attributeService;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    PoliticRepository politicRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    @SneakyThrows
    public ExperienceResponseDTO experienceSave(ExperienceDTO experienceDTO) {
        try{
            var isExperienceExist = experienceRepository.findByTitle(experienceDTO.getTitle());
            if (isExperienceExist.isEmpty()) {
                var experienceModel = ExperienceDTO.experienceDtoToExperienceModel(experienceDTO);
                var categoryModel = categoryRepository.getById(experienceDTO.getIdCategory());
                var attributes = attributeService.listaAtributos(experienceDTO.getAttributes());
                var place = placeRepository.findById(experienceDTO.getIdPlace());
                if (categoryModel.getId() < 5){
                    experienceModel.setPolitics(politicRepository.findById(categoryModel.getId()).get());
                } else {
                    experienceModel.setPolitics(politicRepository.findById((long) 5).get());
                }
                experienceModel.setPlace(place.get());
                experienceModel.setCategory(categoryModel);
                experienceModel.setAttributes(attributes);
                experienceRepository.save(experienceModel);
                return ExperienceResponseDTO.experienceModelToResponseDto(experienceModel);
            }
            else {
                throw new BusinessException("El Titulo de la experiencia ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public ExperienceResponseDTO getExperienceById(Long id) {
        try {
            var isExperienceExist = experienceRepository.findById(id);
            if (isExperienceExist.isPresent()) {
                var categoryDTO = CategoryResponseDTO.categoryModelToCategoryDto(isExperienceExist.get().getCategory());
                var listAttributes = AttributeResponseDTO.attributeModelToAttributeList(isExperienceExist.get().getAttributes());
                Set<AttributeResponseDTO> attributeDTO = new HashSet<>(listAttributes);
                ExperienceResponseDTO experienceResponseDTO = ExperienceResponseDTO.experienceModelToResponseDto(isExperienceExist.get());
                experienceResponseDTO.setCategory(categoryDTO);
                experienceResponseDTO.setAttributes(attributeDTO);
                return experienceResponseDTO;
            } else {
                throw new BusinessException("El id de la experiencia no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public ExperienceResponseDTO updateExperience(Long id, ExperienceDTO experienceDTO) {
        try {
            var isExpirienceExist = experienceRepository.findById(id);
            if (isExpirienceExist.isPresent()) {
                var experienceModel = isExpirienceExist.get();
                experienceModel.setTitle(experienceDTO.getTitle());
                experienceModel.setDescription(experienceDTO.getDescription());
                experienceModel.setImages(experienceDTO.getImages());
                experienceModel.setCategory(categoryRepository.getById(experienceDTO.getIdCategory()));
                experienceModel.setPlace(placeRepository.findById(experienceDTO.getIdPlace()).get());
                experienceModel.setDurationInDays(experienceDTO.getDurationInDays());
                experienceModel.setAmountForPerson(experienceDTO.getAmountForPerson());
                experienceRepository.save(experienceModel);
                return ExperienceResponseDTO.experienceModelToResponseDto(experienceModel);
            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<ExperienceResponseDTO> experienceByCategoryList(Long id) {
        try{
            List<Experience> list = experienceRepository.findAllForCategory(id);
            if (!list.isEmpty()) {
                return ExperienceResponseDTO.experienceModelToExpirienceList(list);
            } else {
                throw new BusinessException("No hay experiencia que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<ExperienceResponseDTO> experienceByCityList(Long id) {
        try{
            var city = cityRepository.findById(id).get().getName();
            List<Experience> list = experienceRepository.findAllForCity(city);
            if (!list.isEmpty()) {
                return ExperienceResponseDTO.experienceModelToExpirienceList(list);
            } else {
                throw new BusinessException("No hay experiencia que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public String adjustDateString(String input) {
        String[] parts = input.split("/");
        String part1 = String.format("%02d", Integer.parseInt(parts[0]));
        String part2 = String.format("%02d", Integer.parseInt(parts[1]));
        return part1 + "/" + part2 + "/" + parts[2];
    }

    public boolean CrossReserve(Reserve reserva1, Reserve reserva2) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate startDay1 = LocalDate.parse(adjustDateString(reserva1.getStartDay()), dateFormatter);
        LocalDate endDay1 = LocalDate.parse(adjustDateString(reserva1.getEndDay()), dateFormatter);
        LocalDate startDay2 = LocalDate.parse(adjustDateString(reserva2.getStartDay()), dateFormatter);
        LocalDate endDay2 = LocalDate.parse(adjustDateString(reserva2.getEndDay()), dateFormatter);

        if (startDay1.isBefore(startDay2) && endDay1.isAfter(startDay2) && endDay1.isBefore(endDay2)) {
            // Reserva 1 inicia antes y termina dentro de Reserva 2
            return true;
        } else if (startDay1.isAfter(startDay2) && startDay1.isBefore(endDay2) && endDay1.isAfter(endDay2)) {
            // Reserva 1 inicia dentro de Reserva 2 y termina después
            return true;
        } else if (startDay1.isBefore(startDay2) && endDay1.isAfter(endDay2)) {
            // Reserva 1 inicia antes y termina después de Reserva 2
            return true;
        } else if (startDay1.isEqual(startDay2) && endDay1.isEqual(endDay2)) {
            // Reserva 1 es igual a Reserva 2
            return true;
        } else if (startDay1.isEqual(startDay2) && endDay1.isBefore(endDay2)) {
            // Reserva 1 inicia igual que Reserva 2 y termina dentro de Reserva 2
            return true;
        } else if (startDay1.isAfter(startDay2) && endDay1.isEqual(endDay2)) {
            // Reserva 1 inicia dentro de Reserva 2 y termina igual que Reserva 2
            return true;
        } else if (startDay1.isEqual(startDay2) && endDay1.isAfter(startDay2) && endDay1.isBefore(endDay2)) {
            // Reserva 1 y Reserva 2 comienzan y terminan al mismo tiempo
            return true;
        } else if (startDay1.isAfter(startDay2) && endDay1.isBefore(endDay2)) {
            // Reserva 1 está completamente contenida dentro de Reserva 2
            return true;
        } else if (startDay2.isAfter(startDay1) && endDay2.isBefore(endDay1)) {
            // Reserva 2 está completamente contenida dentro de Reserva 1
            return true;
        } else if (startDay1.isEqual(startDay2) && endDay1.isBefore(endDay2)) {
            // Reserva 1 comienza al mismo tiempo que Reserva 2, pero termina antes
            return true;
        } else if (startDay1.isEqual(startDay2) && endDay2.isBefore(endDay1)) {
            // Reserva 1 comienza al mismo tiempo que Reserva 2, pero termina después
            return true;
        }
        return false;
    }

    @Override
    @SneakyThrows
    public List<ExperienceResponseDTO> experienceByCityAndTimeList(BookingTimeIntervalDTO bookingTimeIntervalDTO) {
        try {
            Reserve buscador = new Reserve(bookingTimeIntervalDTO.getStartDay(), bookingTimeIntervalDTO.getEndDay());

            var city = cityRepository.findById(bookingTimeIntervalDTO.getIdCity()).get();

            List<Experience> list = experienceRepository.findAllForCity(city.getName());
            List<Experience> filteredList = new CopyOnWriteArrayList<>(list);

            for (Experience e : list) {
                var listBooking = bookingRepository.findAllForExperienceId(e.getId());
                for (Booking b : listBooking) {
                    if (b != null) {
                        Reserve reserve = new Reserve(b.getStartDate(), b.getEndDate());
                        if (CrossReserve(buscador, reserve)) {
                            filteredList.remove(e);
                        }
                    }
                }
            }
            if (!filteredList.isEmpty()) {
                return ExperienceResponseDTO.experienceModelToExpirienceList(filteredList);
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
//        try{
//            Reserve buscador = new Reserve(new Date(bookingTimeIntervalDTO.getStartDay()), new Date(bookingTimeIntervalDTO.getEndDay()));
//            var city = cityRepository.findById(bookingTimeIntervalDTO.getIdCity()).get().getName();
//            List<Experience> list = experienceRepository.findAllForCity(city);
//            for (Experience e: list) {
//                var listBooking = bookingRepository.findAllForExperienceId(e.getId());
//                for (Booking b: listBooking) {
//                    if (b != null){
//                        Reserve reserve = new Reserve(new Date(b.getEndDate()),new Date(b.getStartDate()));
//                        if (CrossReserve(reserve, buscador)) {
//                            list.remove(e);
//                        }
//                    }
//                }
//            }
//            if (!list.isEmpty()) {
//                return ExperienceResponseDTO.experienceModelToExpirienceList(list);
//            } else {
//                throw new BusinessException("No hay experiencia que mostrar");
//            }
//        } catch (RuntimeException e) {
//            throw new BusinessException(e.getMessage());
//        }
//    }

    @Override
    @SneakyThrows
    public List<BookingTimeIntervalResponseDTO> bookingByExperience(Long id) {
        try {
            var isExperienceExist = experienceRepository.findById(id);
            if (isExperienceExist.isPresent()) {
                List<Booking> list = bookingRepository.findAllForExperienceId(id);
                if (!list.isEmpty()) {
                    return BookingTimeIntervalResponseDTO.toBookingTimeIntervalResponseDTOList(list);
                } else {
                    return null;
                }
            } else {
                throw new BusinessException("El id de la experiencia no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void experienceDelete(Long id) {
        try {
            var isExperienceExist = experienceRepository.findById(id);
            if (isExperienceExist.isPresent()) {
                var experienceModel = isExperienceExist.get();
                experienceModel.delete();
                experienceRepository.save(experienceModel);
            } else {
                throw new BusinessException("El id de la experiencia no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<ExperienceResponseDTO> experienceRecommendList() {
        try{
            List<Experience> recomends = experienceRepository.findRecommend();
            if(!recomends.isEmpty()){
                return ExperienceResponseDTO.experienceModelsToResponseDtos(recomends);
            } else {
                throw new BusinessException("No se encontraron experiencias que recomendar");
            }

        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public ExperienceResponseDTO experienceUpdateCategory(Long idExperience, Long idCategory) {
        try {
            var isExperienceExist = experienceRepository.findById(idExperience);
            if (isExperienceExist.isPresent()) {
                var experienceModel = isExperienceExist.get();
                var isCategoryExist = categoryRepository.findById(idCategory);
                if (isCategoryExist.isPresent()){
                    var categoryModel = isCategoryExist.get();
                    experienceModel.setCategory(categoryModel);
                    experienceRepository.save(experienceModel);
                    return ExperienceResponseDTO.experienceModelToResponseDto(experienceModel);
                } else {
                    throw new BusinessException("El id de la categoria no existe");
                }
            } else {
                throw new BusinessException("El id de la experiencia no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
