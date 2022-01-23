package com.to.wms.controller.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AddressPostDto {

    @JsonProperty(value = "post_code", required = true)
    @NotBlank(message = "Postcode can not be empty!")
    private String postCode;

    @NotBlank(message = "Country can not be empty!")
    private String country;

    @NotBlank(message = "City can not be empty!")
    private String city;

    @NotBlank(message = "Street can not be empty!")
    private String street;

    @NotBlank(message = "Number can not be empty!")
    private String number;

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
