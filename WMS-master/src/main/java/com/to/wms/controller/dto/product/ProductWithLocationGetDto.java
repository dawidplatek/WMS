package com.to.wms.controller.dto.product;

import com.to.wms.controller.dto.location.LocationGetDto;

public class ProductWithLocationGetDto extends ProductGetDto {

    private LocationGetDto location;

    public ProductWithLocationGetDto() {
    }

    public ProductWithLocationGetDto(LocationGetDto location) {
        this.location = location;
    }

    public ProductWithLocationGetDto(String name, String description, Integer quantity, String category, LocationGetDto location) {
        super(name, description, quantity, category);
        this.location = location;
    }

    public LocationGetDto getLocation() {
        return location;
    }

    public void setLocation(LocationGetDto location) {
        this.location = location;
    }
}
