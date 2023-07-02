package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.expirience.Attribute;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttributeDTO {

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    private String name;

//    IMAGEN PERMANECE EN MANTENIMIENTO
//    @NotNull(message = "El campo requerido no puede ser nulo")
//    @NotBlank(message = "El campo requerido no puede quedar en blanco")
//    private String image;

    public static Attribute attributeDtoToAttributeModel(AttributeDTO attributeDTO){
        return Attribute.builder()
                .name(attributeDTO.getName())
//                .image(attributeDTO.getImage())
                .build();
    }
}
