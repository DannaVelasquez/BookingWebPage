package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.CategoryDTO;
import com.digitalhouse.digitalexpirience.dto.response.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {
    CategoryResponseDTO categorySave(CategoryDTO categoryDTO);

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO categoryUpdate(Long id, CategoryDTO categoryDTO);

    void categoryDelete(Long id);

    List<CategoryResponseDTO> getAllCategories();
}
