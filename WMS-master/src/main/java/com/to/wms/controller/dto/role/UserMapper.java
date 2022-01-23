package com.to.wms.controller.dto.role;

import com.to.wms.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "department", ignore = true)
    User userPostDtoToUser(UserPostDto userPostDto);
}
