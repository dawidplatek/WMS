package com.to.wms.service;

import com.to.wms.controller.dto.GenericResponseDto;
import com.to.wms.controller.dto.category.CategoryGetDto;
import com.to.wms.controller.dto.category.CategoryMapper;
import com.to.wms.controller.dto.category.CategoryPostDto;
import com.to.wms.model.Category;
import com.to.wms.repository.CategoryRepository;
import com.to.wms.service.exceptions.CategoryAlreadyExistException;
import com.to.wms.service.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper mapper) {
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public CategoryGetDto getCategoryByName(String categoryName) throws CategoryNotFoundException {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(CategoryNotFoundException::new);
        return mapper.categoryToCategoryGetDto(category);
    }

    public CategoryGetDto addCategory(CategoryPostDto categoryPostDto) throws CategoryAlreadyExistException {
        if (categoryRepository.findByName(categoryPostDto.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }
        Category category = mapper.categoryPostDtoToCategory(categoryPostDto);
        categoryRepository.save(category);
        return mapper.categoryToCategoryGetDto(category);
    }

    public CategoryGetDto editCategory(String categoryName, CategoryPostDto categoryToUpdate) throws CategoryNotFoundException, CategoryAlreadyExistException {
        Category category = categoryRepository.findByName(categoryName).orElseThrow(CategoryNotFoundException::new);
        if (categoryRepository.findByName(categoryToUpdate.getName()).isPresent()) {
            throw new CategoryAlreadyExistException();
        }
        category.setName(categoryToUpdate.getName());
        categoryRepository.save(category);
        return mapper.categoryToCategoryGetDto(category);
    }

    public List<CategoryGetDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(mapper::categoryToCategoryGetDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public GenericResponseDto deleteByName(String name) throws CategoryNotFoundException {
        Category category = categoryRepository.findByName(name).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
        return new GenericResponseDto("Successfully deleted category!");
    }

}
