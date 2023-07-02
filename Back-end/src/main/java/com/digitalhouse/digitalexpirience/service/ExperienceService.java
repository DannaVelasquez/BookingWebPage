package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.BookingTimeIntervalDTO;
import com.digitalhouse.digitalexpirience.dto.request.ExperienceDTO;
import com.digitalhouse.digitalexpirience.dto.response.BookingResponseDTO;
import com.digitalhouse.digitalexpirience.dto.response.BookingTimeIntervalResponseDTO;
import com.digitalhouse.digitalexpirience.dto.response.ExperienceResponseDTO;
import java.util.List;

public interface ExperienceService {
    ExperienceResponseDTO experienceSave(ExperienceDTO experienceDTO);

    void experienceDelete(Long id);

    List<ExperienceResponseDTO> experienceRecommendList();

    ExperienceResponseDTO getExperienceById(Long id);

    ExperienceResponseDTO experienceUpdateCategory(Long idExperience, Long idCategory);

    ExperienceResponseDTO updateExperience(Long id, ExperienceDTO experienceDTO);

    List<ExperienceResponseDTO> experienceByCategoryList(Long id);

    List<ExperienceResponseDTO> experienceByCityList(Long id);

    List<BookingTimeIntervalResponseDTO> bookingByExperience(Long id);

    List<ExperienceResponseDTO> experienceByCityAndTimeList(BookingTimeIntervalDTO bookingTimeIntervalDTO);
}
