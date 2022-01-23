package com.to.wms.controller;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.category.CategoryGetDto;
import com.to.wms.controller.dto.category.CategoryPostDto;
import com.to.wms.service.CategoryService;
import com.to.wms.service.exceptions.CategoryAlreadyExistException;
import com.to.wms.service.exceptions.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<CategoryGetDto> getCategoryByName(
            @PathVariable String name
    ) throws CategoryNotFoundException {
        CategoryGetDto category = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryGetDto>> getAllCategories() {
        List<CategoryGetDto> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping()
    public ResponseEntity<CategoryGetDto> addCategory(
            @Valid @RequestBody CategoryPostDto category
    ) throws CategoryAlreadyExistException {
        return new ResponseEntity<>(categoryService.addCategory(category), HttpStatus.CREATED);
    }

    @PutMapping("/{name}")
    public ResponseEntity<CategoryGetDto> updateCategory(
            @Valid @RequestBody CategoryPostDto categoryToUpdate,
            @PathVariable String name
    ) throws CategoryNotFoundException, CategoryAlreadyExistException {
        return new ResponseEntity<>(categoryService.editCategory(name, categoryToUpdate), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<GenericResponseDto> deleteCategory(@PathVariable String name) throws CategoryNotFoundException {
        return ResponseEntity.ok(categoryService.deleteByName(name));
    }
}
