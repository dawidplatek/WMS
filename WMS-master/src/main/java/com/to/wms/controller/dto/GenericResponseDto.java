package com.to.wms.controller.dto;

public class GenericResponseDto {

    private String message;

    public GenericResponseDto() {
    }

    public GenericResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
