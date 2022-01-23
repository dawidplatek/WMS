package com.to.wms.repository;

import com.to.wms.model.Location;
import com.to.wms.model.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findProductByNameAndLocation(String productName, Location location);
    List<Product> findByName(String name);

    @Aggregation(pipeline = {
            "{$lookup: {from: 'location', localField: 'location', foreignField: '_id', as: 'locationDoc'}}",
            "{$lookup: {from: 'department', localField: 'locationDoc.department', foreignField: '_id', as: 'locationDepartmentDoc'}}",
            "{$match: {'locationDepartmentDoc.name': ?0}}",
            "{$project: {locationDoc: 0, locationDepartmentDoc:0}}"
    })
    List<Product> findAllProductsByDepartmentName(String departmentName);

    @Aggregation(pipeline = {
            "{$lookup: {from: 'category', localField: 'category', foreignField: '_id', as: 'categoryDoc'}}",
            "{$match: {'categoryDoc.name': '?0'}}",
            "{$project: {categoryDoc: 0}}"
    })
    List<Product> findAllProductsByCategoryName(String categoryName);


}
