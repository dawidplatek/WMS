package com.to.wms.controller.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductPostDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    private Integer quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
