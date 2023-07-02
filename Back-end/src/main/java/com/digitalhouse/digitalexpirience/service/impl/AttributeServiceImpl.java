package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.AttributeDTO;
import com.digitalhouse.digitalexpirience.dto.response.AttributeResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.model.expirience.Attribute;
import com.digitalhouse.digitalexpirience.repository.AttributeRepository;
import com.digitalhouse.digitalexpirience.service.AttributeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    AttributeRepository attributeRepository;
    @Override
    @SneakyThrows
    public AttributeResponseDTO attributeSave(AttributeDTO attributeDTO) {
        try{
            var isCategoryExist = attributeRepository.findByName(attributeDTO.getName());
            if (isCategoryExist.isEmpty()) {
                var attributeModel = AttributeDTO.attributeDtoToAttributeModel(attributeDTO);
                attributeRepository.save(attributeModel);
                return AttributeResponseDTO.attributeModelToAttributeDto(attributeModel);
            }
            else {
                throw new BusinessException("El nombre del atributo ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void attributeDelete(Long id) {
        try {
            var isAttributeExist = attributeRepository.findById(id);
            if (isAttributeExist.isPresent()) {
                var attributeModel = isAttributeExist.get();
                attributeModel.delete();
                attributeRepository.save(attributeModel);
            } else {
                throw new BusinessException("El id de la categoria no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public AttributeResponseDTO getAttributeById(Long id) {
        try {
            var isAttributeExist = attributeRepository.findById(id);
            if (isAttributeExist.isPresent()) {
                return AttributeResponseDTO.attributeModelToAttributeDto(isAttributeExist.get());
            } else {
                throw new BusinessException("El id del atributo no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public AttributeResponseDTO attributeUpdate(Long id, AttributeDTO attributeDTO) {
        try {
            var isAttributeExist = attributeRepository.findById(id);
            if (isAttributeExist.isPresent()) {
                var attributeModel = isAttributeExist.get();
                attributeModel.setName(attributeDTO.getName());
//              IMAGEN PERMANECE EN MANTENIMIENTO
//                attributeModel.setImage(attributeDTO.getImage());
                attributeRepository.save(attributeModel);
                return AttributeResponseDTO.attributeModelToAttributeDto(attributeModel);
            } else {
                throw new BusinessException("El id del atributo no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public Set<AttributeResponseDTO> getAllAttributes() {
        try{
            var list = attributeRepository.findAll();
            Set<Attribute> attributes = new HashSet<>(list);
            if (!list.isEmpty()) {
                return AttributeResponseDTO.attributeModelToAttributeList(attributes);
            } else {
                throw new BusinessException("No hay atributos que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public Set<Attribute> listaAtributos(ArrayList<Long> idAttributes){
        return new HashSet<>(attributeRepository.findAllById(idAttributes));
    }
}
