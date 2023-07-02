package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.PlaceDTO;
import com.digitalhouse.digitalexpirience.dto.response.CityResponseDTO;
import com.digitalhouse.digitalexpirience.dto.response.CountryResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.model.place.Place;
import com.digitalhouse.digitalexpirience.repository.CityRepository;
import com.digitalhouse.digitalexpirience.repository.PlaceRepository;
import com.digitalhouse.digitalexpirience.service.CityService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Override
    @SneakyThrows
    public CityResponseDTO citySave(CityDTO cityDTO) {
        try {
            var isCityExist = cityRepository.findByName(cityDTO.getName());
            if (isCityExist.isEmpty()) {
                var cityModel = CityDTO.cityDtoToCityModel(cityDTO);
                var placeSet = cityDTO.getPlaces() != null ? new HashSet<>(placeRepository.findAllById(cityDTO.getPlaces())) :new HashSet<Place>();
                cityModel.setPlaces((Set<Place>) placeSet);
                cityRepository.save(cityModel);
                return CityResponseDTO.cityModelToCityDto(cityModel);
            } else {
                throw new BusinessException("El nombre de la ciudad ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CityResponseDTO getCityById(Long id) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()){
                return CityResponseDTO.cityModelToCityDto(isCityExist.get());
            } else {
                throw new BusinessException("El id de la ciudad no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void cityDelete(Long id) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()) {
                var cityModel = isCityExist.get();
                cityModel.delete();
                cityRepository.save(cityModel);
            } else {
                throw new BusinessException("El id de la categoria no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CityResponseDTO cityUpdate(Long id, CityDTO cityDTO) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()) {
                var cityModel = isCityExist.get();
                cityModel.setName(cityDTO.getName());
                if (cityDTO.getPlaces() != null){
                    cityModel.setPlaces(new HashSet<Place>(placeRepository.findAllById(cityDTO.getPlaces())));
                }
                cityRepository.save(cityModel);
                return CityResponseDTO.cityModelToCityDto(cityModel);
            } else {
                throw new BusinessException("El id de la ciudad no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<CityResponseDTO> getAllCities() {
        try {
            var list = cityRepository.findAll();
            if(!list.isEmpty()){
                return CityResponseDTO.cityModelToCityList(list);
            } else {
                throw new BusinessException("No hay ciudades que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CityResponseDTO cityAddPlace(Long id, PlaceDTO placeDTO) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()){
                var cityModel = isCityExist.get();
                cityModel.getPlaces().add(PlaceDTO.placeDtoToPlaceModel(placeDTO));
                cityRepository.save(cityModel);
                return CityResponseDTO.cityModelToCityDto(cityModel);
            } else {
                throw new BusinessException("El id de la ciudad no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CityResponseDTO cityRemovePlace(Long id, PlaceDTO placeDTO) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()){
                var cityModel = isCityExist.get();
                cityModel.getPlaces().remove(PlaceDTO.placeDtoToPlaceModel(placeDTO));
                cityRepository.save(cityModel);
                return CityResponseDTO.cityModelToCityDto(cityModel);
            } else {
                throw new BusinessException("El id de la ciudad no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CityResponseDTO cityAddPlace(Long id, Place place) {
        try {
            var isCityExist = cityRepository.findById(id);
            if (isCityExist.isPresent()){
                var cityModel = isCityExist.get();
                cityModel.getPlaces().add(place);
                cityRepository.save(cityModel);
                return CityResponseDTO.cityModelToCityDto(cityModel);
            } else {
                throw new BusinessException("El id de la ciudad no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
