package com.to.wms.controller.dto.department;

import com.to.wms.controller.dto.address.AddressGetDto;
import com.to.wms.model.Address;
import com.to.wms.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring"
)
public interface DepartmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    Department departmentPostDtoToDepartment(DepartmentPostDto departmentPostDto);


    @Mapping(target = "addressGetDto", source = "address", qualifiedByName = "addressToGetDto")
    DepartmentGetDto departmentToDepartmentGetDto(Department department);

    @Named("addressToGetDto")
    default AddressGetDto addressToAddressGetDto(Address address) {
        return new AddressGetDto(
                address.getCountry(),
                address.getCity(),
                address.getPostCode(),
                address.getStreet(),
                address.getNumber()
        );
    }

}
