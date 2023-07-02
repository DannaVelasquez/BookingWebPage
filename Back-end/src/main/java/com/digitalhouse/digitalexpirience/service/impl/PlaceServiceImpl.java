package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.CountryResponseDTO;
import com.digitalhouse.digitalexpirience.dto.response.PlaceResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.repository.CityRepository;
import com.digitalhouse.digitalexpirience.repository.CountryRepository;
import com.digitalhouse.digitalexpirience.repository.PlaceRepository;
import com.digitalhouse.digitalexpirience.service.CityService;
import com.digitalhouse.digitalexpirience.service.PlaceService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    CityService cityService;

    @Autowired
    CountryRepository countryRepository;

    @Override
    @SneakyThrows
    public PlaceResponseDTO placeSave(PlaceDTO placeDTO) {
        try {
            var isPlaceExist = placeRepository.findByName(placeDTO.getName());
            if (isPlaceExist.isEmpty()) {
                var placeModel = PlaceDTO.placeDtoToPlaceModel(placeDTO);

                var isCityExist = cityRepository.findById(placeDTO.getIdCity());
                if (isCityExist.isPresent()) {
                    placeModel.setCity(isCityExist.get().getName());
                } else {
                    throw new BusinessException("El id de la ciudad no existe");
                }

                var isCountyExist = countryRepository.findById(placeDTO.getIdCountry());
                if (isCountyExist.isPresent()) {
                    placeModel.setCountry(isCountyExist.get().getName());
                } else {
                    throw new BusinessException("El id del pais no existe");
                }
                placeRepository.save(placeModel);
                cityService.cityAddPlace(placeDTO.getIdCity(), placeModel);
                return PlaceResponseDTO.placeModeltoPlaceResponseDTO(placeModel);
            } else {
                throw new BusinessException("El nombre de la ubicacion ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public PlaceResponseDTO getPlaceById(Long id) {
        try {
            var isPlaceExist = placeRepository.findById(id);
            if (isPlaceExist.isPresent()){
                return PlaceResponseDTO.placeModeltoPlaceResponseDTO(isPlaceExist.get());
            } else {
                throw new BusinessException("El id de la ubicacion no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void placeDelete(Long id) {
        try {
            var isPlaceExist = placeRepository.findById(id);
            if (isPlaceExist.isPresent()){
                var placeModel = isPlaceExist.get();
                placeModel.delete();
                placeRepository.save(placeModel);
            } else {
                throw new BusinessException("El id de la ubicacion no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public PlaceResponseDTO placeUpdate(Long id, PlaceDTO placeDTO) {
        try {
            var isPlaceExist = placeRepository.findById(id);
            if (isPlaceExist.isPresent()){
                var placeModel = isPlaceExist.get();
                placeModel.setName(placeDTO.getName());

                var isCityExist = cityRepository.findById(placeDTO.getIdCity());
                if (isCityExist.isPresent()) {
                    placeModel.setCity(isCityExist.get().getName());
                } else {
                    throw new BusinessException("El id de la ciudad no existe");
                }

                var isCountyExist = countryRepository.findById(placeDTO.getIdCountry());
                if (isCountyExist.isPresent()) {
                    placeModel.setCountry(isCountyExist.get().getName());
                } else {
                    throw new BusinessException("El id del pais no existe");
                }

                placeRepository.save(placeModel);
                cityService.cityAddPlace(placeDTO.getIdCity(), placeModel);
                return PlaceResponseDTO.placeModeltoPlaceResponseDTO(placeModel);
            } else {
                throw new BusinessException("El id de la ubicacion no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<PlaceResponseDTO> getAllPlace() {
        try {
            var list = placeRepository.findAll();
            if(!list.isEmpty()){
                return PlaceResponseDTO.placeModelsToPlaceResponseDtos(list);
            } else {
                throw new BusinessException("No hay ubicaciones que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
