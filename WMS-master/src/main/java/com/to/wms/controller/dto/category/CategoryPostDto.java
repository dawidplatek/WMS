package com.to.wms.controller.dto.category;

import javax.validation.constraints.NotBlank;

public class CategoryPostDto {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
