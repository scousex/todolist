package com.sample.entities.repos;

import com.sample.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

//@RepositoryRestResource(collectionResourceRel = "users", path="users")
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(@Param("username") String name );
    void createUser(@Param("username") String username, @Param("password") String password);

}
