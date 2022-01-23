package com.to.wms.controller.dto.address;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddressGetDto {

    private String country;
    private String city;
    @JsonProperty("post_code")
    private String postCode;
    private String street;
    private String number;

    public AddressGetDto() {
    }

    public AddressGetDto(String country, String city, String postCode, String street, String number) {
        this.country = country;
        this.city = city;
        this.postCode = postCode;
        this.street = street;
        this.number = number;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
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
