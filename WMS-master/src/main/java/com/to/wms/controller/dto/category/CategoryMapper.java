package com.to.wms.controller.dto.category;

import com.to.wms.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    Category categoryPostDtoToCategory(CategoryPostDto categoryPostDto);
    CategoryGetDto categoryToCategoryGetDto(Category category);
}
