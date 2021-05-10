package com.chatter.repositories;


import com.chatter.model.User.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "SELECT * FROM user WHERE user_name = :user_name",
  nativeQuery = true)
  User getUserWithUserName(@Param("user_name") String user_name);

  @Query(value = "SELECT * FROM user WHERE login = :login",
  nativeQuery = true)
  User getUserWithLogin(@Param("login") String login);

  @Query(value = "SELECT * FROM user WHERE email = :email",
  nativeQuery = true)
  User getUserWithEmail(@Param("email") String email);

  @Query(value = "SELECT * FROM user WHERE id = :id",
  nativeQuery = true)
  User getUserWithId(@Param("id") Integer id);

  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET login = :val WHERE id = :id",
  nativeQuery = true)
  void changeLogin(@Param("id") Integer id, @Param("val") String val );

}
