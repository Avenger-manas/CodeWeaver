package com.dolpi.CodeWeaver.Repository;

import com.dolpi.CodeWeaver.Entity.User;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Userrepository extends MongoRepository<User,String> {

    User findByUsername(String username);

    boolean existsByEmail(String email);
}
