package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.AttributeDTO;
import com.digitalhouse.digitalexpirience.dto.response.AttributeResponseDTO;
import java.util.Set;

public interface AttributeService {
     AttributeResponseDTO attributeSave (AttributeDTO attributeDTO);

    void attributeDelete(Long id);

    AttributeResponseDTO getAttributeById(Long id);

    AttributeResponseDTO attributeUpdate(Long id, AttributeDTO attributeDTO);

    Set<AttributeResponseDTO> getAllAttributes();
}
