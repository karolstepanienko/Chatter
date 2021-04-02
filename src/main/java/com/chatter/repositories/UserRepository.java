package com.chatter.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// Project imports:
import com.chatter.classes.User.User;


public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "SELECT * FROM user WHERE name = :name",
  nativeQuery = true)
  User getUserWithName(@Param("name") String name);
}
