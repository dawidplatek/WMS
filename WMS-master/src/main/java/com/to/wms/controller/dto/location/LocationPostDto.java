package com.to.wms.controller.dto.location;

import javax.validation.constraints.NotBlank;

public class LocationPostDto {

    @NotBlank
    private String shelf;

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

}
