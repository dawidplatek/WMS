package com.to.wms.repository;

import com.to.wms.model.Authority;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorityRepository extends MongoRepository<Authority, String> {

    List<Authority> findAllByNameIn(List<String> names);
    Authority findByName(String name);

}
