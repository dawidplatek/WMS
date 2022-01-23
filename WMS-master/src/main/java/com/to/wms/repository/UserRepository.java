package com.to.wms.repository;

import com.to.wms.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findUserByLogin(String login);
    @Query(value = "{}", count = true)
    int getAllCount();
}
