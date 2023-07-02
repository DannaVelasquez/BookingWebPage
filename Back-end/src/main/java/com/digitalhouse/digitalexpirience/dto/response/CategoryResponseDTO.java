package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Category;
import lombok.Builder;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class CategoryResponseDTO {
    private Long id;

    private String name;

    private String description;

    private String image;

    public static CategoryResponseDTO categoryModelToCategoryDto(Category categoryModel) {
        return CategoryResponseDTO.builder()
                .id(categoryModel.getId())
                .name(categoryModel.getName())
                .description(categoryModel.getDescription())
                .image(categoryModel.getImage())
                .build();
    }

    public static List<CategoryResponseDTO> categoryModelToCategoryList(List<Category> listCategory) {
        if(listCategory == null){
            return null;
        }
        List<CategoryResponseDTO> listCategoryDTO = new ArrayList<>();
        for (Category category: listCategory) {
            listCategoryDTO.add(categoryModelToCategoryDto(category));
        }
        return listCategoryDTO;
    }
}
