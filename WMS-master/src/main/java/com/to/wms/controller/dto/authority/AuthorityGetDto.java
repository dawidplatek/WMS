package com.to.wms.controller.dto.authority;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class AuthorityGetDto {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
