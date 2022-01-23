package com.to.wms.repository;

import com.to.wms.model.Department;
import com.to.wms.model.Location;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LocationRepository extends MongoRepository<Location, String> {
    @Aggregation(pipeline = {
            "{$lookup: {from: 'department', localField: 'department', foreignField: '_id', as: 'departmentDoc'}}",
            "{$match: {'departmentDoc.name': '?0'}}",
            "{$project: {departmentDoc: 0}}"
    })
    List<Location> findAllLocationsByDepartmentName(String departmentName);

    Optional<Location> findByDepartmentAndShelf(Department department, String shelf);

}
