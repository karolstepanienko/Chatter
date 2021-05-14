package com.chatter.repositories;

import java.util.Set;

import com.chatter.model.Post.Post;
import com.chatter.model.User.User;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE creator_id = :creator_id",
    nativeQuery = true)
    Iterable<Post> getPostWithCreatorId(@Param("creator_id") Integer creator_id);

    @Query(value = "SELECT * FROM post WHERE privacy = :privacy ORDER BY likes DESC",
    nativeQuery = true)
    Iterable<Post> getPostWithPrivacy(@Param("privacy") Integer privacy);

    @Query(value = "SELECT * FROM post WHERE id = :id",
    nativeQuery = true)
    Post getPostWithId(@Param("id") Integer id);

    @Query(value = "SELECT post_id FROM user_post WHERE user_id = :user_id",
    nativeQuery = true)
    Iterable<Integer> getLikedPost(@Param("user_id") Integer user_id);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET likes = :val WHERE id = :id",
    nativeQuery = true)
    void changeLikes(@Param("id") Integer id, @Param("val") Integer val);

    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET userList = :val WHERE id = :id",
    nativeQuery = true)
    void changeUserList(@Param("id") Integer id, @Param("val") Set<User> userList);

  }
