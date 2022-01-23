package com.to.wms.controller.dto.department;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DepartmentPutDto {

    private String name;

    @JsonProperty("post_code")
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
