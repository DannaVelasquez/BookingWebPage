package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Attribute;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Builder
@Data
public class AttributeResponseDTO {
    private Long id;

    private String name;
//    IMAGEN PERMANECE EN MANTENIMIENTO
//    private String image;

    public static AttributeResponseDTO attributeModelToAttributeDto(Attribute attributeModel) {
        return AttributeResponseDTO.builder()
                .id(attributeModel.getId())
                .name(attributeModel.getName())
//                .image(attributeModel.getImage())
                .build();
    }

    public static Set<AttributeResponseDTO> attributeModelToAttributeList(Set<Attribute> listAttribute) {
        if(listAttribute == null){
            return null;
        }
        Set<AttributeResponseDTO> listAttributeDTO = new HashSet<>();
        for (Attribute attribute: listAttribute) {
            listAttributeDTO.add(attributeModelToAttributeDto(attribute));
        }
        return listAttributeDTO;
    }
}
