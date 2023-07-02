package com.digitalhouse.digitalexpirience.service.impl;

import com.digitalhouse.digitalexpirience.dto.request.CategoryDTO;
import com.digitalhouse.digitalexpirience.dto.response.CategoryResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.repository.CategoryRepository;
import com.digitalhouse.digitalexpirience.service.CategoryService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Override
    @SneakyThrows
    public CategoryResponseDTO categorySave(CategoryDTO categoryDTO) {
        try{
            var isCategoryExist = categoryRepository.findByName(categoryDTO.getName());
            if (isCategoryExist.isEmpty()) {
                var categoryModel = CategoryDTO.categoryDtoToCategoryModel(categoryDTO);
                categoryRepository.save(categoryModel);
                return CategoryResponseDTO.categoryModelToCategoryDto(categoryModel);
            }
            else {
                throw new BusinessException("El nombre de la categoria ya esta en uso");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CategoryResponseDTO getCategoryById(Long id) {
        try {
            var isCategoryExist = categoryRepository.findById(id);
            if (isCategoryExist.isPresent()) {
                return CategoryResponseDTO.categoryModelToCategoryDto(isCategoryExist.get());
            } else {
                throw new BusinessException("El id de la categoria no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void categoryDelete(Long id) {
        try {
            var isCategoryExist = categoryRepository.findById(id);
            if (isCategoryExist.isPresent()) {
                var categoryModel = isCategoryExist.get();
                categoryModel.delete();
                categoryRepository.save(categoryModel);
            } else {
                throw new BusinessException("El id de la categoria no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public List<CategoryResponseDTO> getAllCategories() {
        try{
            var list = categoryRepository.findAll();
            if (!list.isEmpty()) {
                return CategoryResponseDTO.categoryModelToCategoryList(list);
            } else {
                throw new BusinessException("No hay categorias que mostrar");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public CategoryResponseDTO categoryUpdate(Long id, CategoryDTO categoryDTO) {
        try {
            var isCategoryExist = categoryRepository.findById(id);
            if (isCategoryExist.isPresent()) {
                var categoryModel = isCategoryExist.get();
                categoryModel.setName(categoryDTO.getName());
                categoryModel.setDescription(categoryDTO.getDescription());
                categoryModel.setImage(categoryDTO.getImage());
                categoryRepository.save(categoryModel);
                return CategoryResponseDTO.categoryModelToCategoryDto(categoryModel);
            } else {
                throw new BusinessException("El id de la categoria no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }



}
