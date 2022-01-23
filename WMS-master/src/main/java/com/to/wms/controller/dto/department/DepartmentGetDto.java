package com.to.wms.controller.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.to.wms.controller.dto.address.AddressGetDto;

public class DepartmentGetDto {

    private String name;
    @JsonProperty("address")
    private AddressGetDto addressGetDto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressGetDto getAddressGetDto() {
        return addressGetDto;
    }

    public void setAddressGetDto(AddressGetDto addressGetDto) {
        this.addressGetDto = addressGetDto;
    }
}
