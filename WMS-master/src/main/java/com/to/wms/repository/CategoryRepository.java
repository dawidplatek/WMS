package com.to.wms.repository;

import com.to.wms.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String > {
    Optional<Category> findByName(String name);
}
