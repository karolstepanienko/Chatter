package com.chatter.repositories;

import java.util.Optional;

import com.chatter.model.User.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Integer> {

  /**
   * Returns user found by provided userName.
   * @param userName Provided userName.
   * @return User object.
   */
  @Query(value = "SELECT * FROM user WHERE user_name = :userName",
  nativeQuery = true)
  User getUserWithUserName(@Param("userName") String userName);

  /**
   * Returns user found by provided login.
   * @param login Provided login.
   * @return Iterable object with user objects.
   */
  @Query(value = "SELECT * FROM user WHERE login = :login",
  nativeQuery = true)
  Iterable<User> getUserWithLogin(@Param("login") String login);

  /**
   * @param email Provided Email.
   * @return User with given email.
   */
  @Query(value = "SELECT * FROM user WHERE email = :email",
  nativeQuery = true)
  User getUserWithEmail(@Param("email") String email);

  /**
   * @param id Provided user ID.
   * @return User object with a given ID.
   */
  @Query(value = "SELECT * FROM user WHERE id = :id",
  nativeQuery = true)
  User getUserWithId(@Param("id") Integer id);

  /**
   * Changes user login.
   * @param id Provided user ID.
   * @param val New login value.
   */
  @Modifying
  @Transactional
  @Query(value = "UPDATE user SET login = :val WHERE id = :id",
  nativeQuery = true)
  void changeLogin(@Param("id") Integer id, @Param("val") String val);

  Boolean existsByUserName(String userName);

  Boolean existsByEmail(String email);

  Optional<User> findByUserName(String userName);
}
