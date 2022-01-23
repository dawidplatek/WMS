package com.to.wms.repository;

import com.to.wms.model.Department;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends MongoRepository<Department, String> {

    @Aggregation(pipeline = {
            "{$lookup: {from: 'address', localField: 'address', foreignField: '_id', as: 'addressDoc'}}, ",
            "{$match: {'addressDoc.city': '?0'}}",
            "{$project: {addressDoc: 0}}"
    })
    List<Department> findByCityName(String city);

    Optional<Department> findDepartmentByName(String departmentName);
}
