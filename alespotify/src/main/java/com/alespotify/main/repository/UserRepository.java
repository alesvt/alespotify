package com.alespotify.main.repository;

import com.alespotify.main.models.entities.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByEmail(String email);
    


}
