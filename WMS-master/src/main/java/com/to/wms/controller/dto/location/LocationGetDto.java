package com.to.wms.controller.dto.location;

public class LocationGetDto {

    private String shelf;
    private String department;

    public LocationGetDto() {
    }

    public LocationGetDto(String shelf, String department) {
        this.shelf = shelf;
        this.department = department;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
