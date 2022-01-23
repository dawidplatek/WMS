package com.to.wms.controller.dto.product;

import com.to.wms.controller.dto.location.LocationGetDto;
import com.to.wms.model.Category;
import com.to.wms.model.Location;
import com.to.wms.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring"
)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product productPostDtoToProduct(ProductPostDto product);

    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToString")
    ProductGetDto productToProductGetDto(Product product);

    @Mapping(source = "category", target = "category", qualifiedByName = "categoryToString")
    @Mapping(source = "location", target = "location", qualifiedByName = "locationToLocationGetDto")
    ProductWithLocationGetDto productToProductWithLocationGetDto(Product product);

    @Named("categoryToString")
    default String categoryToString(Category category) {
        return category.getName();
    }

    @Named("locationToLocationGetDto")
    default LocationGetDto locationToString(Location location) {
        return new LocationGetDto(location.getShelf(), location.getDepartment().getName());
    }

}
