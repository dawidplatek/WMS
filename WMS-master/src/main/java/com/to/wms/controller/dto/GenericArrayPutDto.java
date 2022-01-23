package com.to.wms.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GenericArrayPutDto {

    @NotNull
    @JsonProperty("new_values")
    private List<String> newValues;

    public List<String> getNewValues() {
        return newValues;
    }

    public void setNewValues(List<String> newValues) {
        this.newValues = newValues;
    }

}
