package com.to.wms.controller.dto.location;

import com.to.wms.controller.dto.product.ProductGetDto;
import com.to.wms.model.Department;
import com.to.wms.model.Location;
import com.to.wms.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring"
)
public interface LocationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "products", ignore = true)
    Location locationPostDtoToLocation(LocationPostDto locationPostDto);

    @Mapping(source = "department", target = "department", qualifiedByName = "departmentToString")
    @Mapping(source = "products", target = "products", qualifiedByName = "productsToProductGetDto")
    LocationWithProductsGetDto locationToLocationWithProductsGetDto(Location location);

    @Mapping(source = "department", target = "department", qualifiedByName = "departmentToString")
    LocationGetDto locationToLocationGetDto(Location location);

    @Named("departmentToString")
    default String departmentToString(Department department) {
        return department.getName();
    }

    @Named("productsToProductGetDto")
    default List<ProductGetDto> productToProductGetDto(Set<Product> products) {
        return products.stream()
                .map(p -> new ProductGetDto(p.getName(), p.getDescription(), p.getQuantity(), p.getCategory().getName()))
                .collect(Collectors.toUnmodifiableList());
    }

}
