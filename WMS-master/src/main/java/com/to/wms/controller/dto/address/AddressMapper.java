package com.to.wms.controller.dto.address;

import com.to.wms.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface AddressMapper {
    @Mapping(target = "id", ignore = true)
    Address addressPostDtoToAddress(AddressPostDto addressPostDto);
    AddressGetDto addressToAddressGetDto(Address address);
}
