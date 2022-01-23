package com.to.wms.controller.dto.authority;

import com.to.wms.model.Authority;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring"
)
public interface AuthorityMapper {
    AuthorityGetDto authorityToAuthorityGetDto(Authority authority);
}
