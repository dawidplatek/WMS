package com.to.wms.controller.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class DepartmentPostDto {

    @NotBlank
    private String name;

    @NotBlank
    @JsonProperty(value = "post_code", required = true)
    private String addressPostCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressPostCode() {
        return addressPostCode;
    }

    public void setAddressPostCode(String addressPostCode) {
        this.addressPostCode = addressPostCode;
    }
}
