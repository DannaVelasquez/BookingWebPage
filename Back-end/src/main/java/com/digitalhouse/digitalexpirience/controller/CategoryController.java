package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.CategoryDTO;

import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Object> categoryControllerSave(@Valid @RequestBody CategoryDTO categoryDTO) {
        var categoryResponseDTO = categoryService.categorySave(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(categoryResponseDTO, "Success", HttpStatus.CREATED));
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> categoryControllerRead(@PathVariable Long id){
        var categoryResponseDTO = categoryService.getCategoryById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(categoryResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> categoryControllerDelete(@PathVariable Long id) {
        categoryService.categoryDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> categoryControllerUpdate(@PathVariable Long id,@Valid @RequestBody CategoryDTO categoryDTO) {
        var categoryResponseDTO = categoryService.categoryUpdate(id, categoryDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(categoryResponseDTO, "Success", HttpStatus.OK));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Object> categoryControllerReadAll(){
        var listCategoryResponseDTO = categoryService.getAllCategories();
        return ResponseEntity.ok().body(ApiResponseDTO.created(listCategoryResponseDTO, "Success", HttpStatus.OK));
    }
}
