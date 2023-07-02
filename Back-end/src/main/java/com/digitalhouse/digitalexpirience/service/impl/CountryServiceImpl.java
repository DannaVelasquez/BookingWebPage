package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.CityDTO;
import com.digitalhouse.digitalexpirience.dto.request.CountryDTO;
import com.digitalhouse.digitalexpirience.dto.response.CountryResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.model.place.City;
import com.digitalhouse.digitalexpirience.model.place.Place;
import com.digitalhouse.digitalexpirience.repository.CityRepository;
import com.digitalhouse.digitalexpirience.repository.CountryRepository;
import com.digitalhouse.digitalexpirience.service.CountryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    CountryRepository countryRepository;

    @Autowired
    CityRepository cityRepository;

    @Override
    @SneakyThrows
    public CountryResponseDTO countrySave(CountryDTO countryDTO) {
        try {
            var isCountryExist = countryRepository.findByName(countryDTO.getName());
            if (isCountryExist.isEmpty()){
                var countryModel = CountryDTO.countryDtoToCountryModel(countryDTO);
                if (countryDTO.getCities() != null){
                    countryModel.setCities(new HashSet<>(cityRepository.findAllById(countryDTO.getCities())));
                }
                countryRepository.save(countryModel);
                return CountryResponseDTO.countryModelToCountryDto(countryModel);
            } else {
                throw new BusinessException("El nombre del pais ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CountryResponseDTO getCountryById(Long id) {
        try {
            var isCountryExist = countryRepository.findById(id);
            if (isCountryExist.isPresent()) {
                return CountryResponseDTO.countryModelToCountryDto(isCountryExist.get());
            } else {
                throw new BusinessException("El id del pais no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void countryDelete(Long id) {
        try {
            var isCountryExist = countryRepository.findById(id);
            if (isCountryExist.isPresent()) {
                var countryModel = isCountryExist.get();
                countryModel.delete();
                countryRepository.save(countryModel);
            } else {
                throw new BusinessException("El id del pais no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CountryResponseDTO countryUpdate(Long id, CountryDTO countryDTO) {
        try {
            var isCountryExist = countryRepository.findById(id);
            if (isCountryExist.isPresent()) {
                var countryModel = isCountryExist.get();
                countryModel.setName(countryDTO.getName());
//                countryModel.setLatitude(countryDTO.getLatitude());
//                countryModel.setLongitude(countryDTO.getLongitude());
                if (countryDTO.getCities() != null){
                    countryModel.setCities(new HashSet<>(cityRepository.findAllById(countryDTO.getCities())));
                }
                countryRepository.save(countryModel);
                return CountryResponseDTO.countryModelToCountryDto(countryModel);
            } else {
                throw new BusinessException("El id del pais no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<CountryResponseDTO> getAllCountries() {
        try {
            var list = countryRepository.findAll();
            if (!list.isEmpty()){
                return CountryResponseDTO.countryModelToCountryList(list);
            } else {
                throw new BusinessException("No hay paises que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CountryResponseDTO countryAddCity(Long id, CityDTO cityDTO) {
        try {
            var isCountryExist = countryRepository.findById(id);
            if (isCountryExist.isPresent()){
                var countryModel = isCountryExist.get();
                countryModel.getCities().add(CityDTO.cityDtoToCityModel(cityDTO));
                countryRepository.save(countryModel);
                return CountryResponseDTO.countryModelToCountryDto(countryModel);
            } else {
                throw new BusinessException("El id del pais no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CountryResponseDTO countryRemoveCity(Long id, CityDTO cityDTO) {
        try {
            var isCountryExist = countryRepository.findById(id);
            if (isCountryExist.isPresent()){
                var countryModel = isCountryExist.get();
                countryModel.getCities().remove(CityDTO.cityDtoToCityModel(cityDTO));
                countryRepository.save(countryModel);
                return CountryResponseDTO.countryModelToCountryDto(countryModel);
            } else {
                throw new BusinessException("El id del pais no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
