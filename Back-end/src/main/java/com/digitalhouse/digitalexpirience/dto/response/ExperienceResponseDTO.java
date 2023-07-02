package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Experience;
import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Builder
@Data
public class ExperienceResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Collection<String> images;
    private int durationInDays;
    private Double amountForPerson;
    private CategoryResponseDTO category;
    private Set<AttributeResponseDTO> attributes;
    private PlaceResponseDTO place;
    private PoliticResponseDTO politics;


    public static ExperienceResponseDTO experienceModelToResponseDto(Experience experienceModel) {
        return ExperienceResponseDTO.builder()
                .id(experienceModel.getId())
                .title(experienceModel.getTitle())
                .description(experienceModel.getDescription())
                .images(experienceModel.getImages())
                .durationInDays(experienceModel.getDurationInDays())
                .amountForPerson(experienceModel.getAmountForPerson())
                .politics(PoliticResponseDTO.politicModelToPoliticResponseDTO(experienceModel.getPolitics()))
                .place(PlaceResponseDTO.placeModeltoPlaceResponseDTO(experienceModel.getPlace()))
                .build();
    }

    public static List<ExperienceResponseDTO> experienceModelToExpirienceList(List<Experience> list) {
        if(list == null){
            return null;
        }
        List<ExperienceResponseDTO> listDTO = new ArrayList<>();
        for (Experience experience: list) {
            listDTO.add(experienceModelToResponseDto(experience));
        }
        return listDTO;
    }

    public static List<ExperienceResponseDTO> experienceModelsToResponseDtos(List<Experience> list) {
        return list.stream().map(mapper -> ExperienceResponseDTO.builder()
                .id(mapper.getId())
                .title(mapper.getTitle())
                .description(mapper.getDescription())
                .images(mapper.getImages())
                .durationInDays(mapper.getDurationInDays())
                .amountForPerson(mapper.getAmountForPerson())
                .category(CategoryResponseDTO.categoryModelToCategoryDto(mapper.getCategory()))
                .attributes(AttributeResponseDTO.attributeModelToAttributeList(mapper.getAttributes()))
                .politics(PoliticResponseDTO.politicModelToPoliticResponseDTO(mapper.getPolitics()))
                .place(PlaceResponseDTO.placeModeltoPlaceResponseDTO(mapper.getPlace()))
                .build()).collect(Collectors.toList());
    }

    public static ExperienceResponseDTO experienceModelToResponseDtoBooking(Experience experienceModel) {
        return ExperienceResponseDTO.builder()
                .id(experienceModel.getId())
                .title(experienceModel.getTitle())
                .description(experienceModel.getDescription())
                .images(experienceModel.getImages())
                .durationInDays(experienceModel.getDurationInDays())
                .amountForPerson(experienceModel.getAmountForPerson())
                .category(CategoryResponseDTO.categoryModelToCategoryDto(experienceModel.getCategory()))
                .attributes(AttributeResponseDTO.attributeModelToAttributeList(experienceModel.getAttributes()))
                .politics(PoliticResponseDTO.politicModelToPoliticResponseDTO(experienceModel.getPolitics()))
                .place(PlaceResponseDTO.placeModeltoPlaceResponseDTO(experienceModel.getPlace()))
                .build();
    }


}
