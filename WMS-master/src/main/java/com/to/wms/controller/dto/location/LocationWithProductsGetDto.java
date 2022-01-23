package com.to.wms.controller.dto.location;


import com.to.wms.controller.dto.product.ProductGetDto;

import java.util.List;

public class LocationWithProductsGetDto {

    private String shelf;
    private String department;
    private List<ProductGetDto> products;

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

    public List<ProductGetDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductGetDto> products) {
        this.products = products;
    }
}
