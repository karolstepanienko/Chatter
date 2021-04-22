package com.chatter.repositories;


import com.chatter.model.User.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "SELECT * FROM user WHERE name = :name",
  nativeQuery = true)
  User getUserWithName(@Param("name") String name);

  @Query(value = "SELECT * FROM user WHERE login = :login",
  nativeQuery = true)
  User getUserWithLogin(@Param("login") String login);

  @Query(value = "SELECT * FROM user WHERE email = :email",
  nativeQuery = true)
  User getUserWithEmail(@Param("email") String email);
}
