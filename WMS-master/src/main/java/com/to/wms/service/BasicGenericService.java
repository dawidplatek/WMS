package com.to.wms.service;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public abstract class BasicGenericService<RepositoryT extends MongoRepository<?, String>> {

    protected final RepositoryT repository;

    public BasicGenericService(RepositoryT repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<?> getAll() {
        return repository.findAll();
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
