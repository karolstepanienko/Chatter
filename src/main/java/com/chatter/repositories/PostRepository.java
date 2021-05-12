package com.chatter.repositories;

import com.chatter.model.Post.Post;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE creatorId = :creatorId",
    nativeQuery = true)
    Post getPostWithCreatorId(@Param("creatorId") Integer creatorId);

    @Query(value = "SELECT * FROM post WHERE privacy = :privacy",
    nativeQuery = true)
    Iterable<Post> getPostWithPrivacy(@Param("privacy") Integer privacy);

    @Query(value = "SELECT * FROM post WHERE id = :id",
    nativeQuery = true)
    Post getPostWithId(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE post SET likes = :val WHERE id = :id",
    nativeQuery = true)
    void changeLikes(@Param("id") Integer id, @Param("val") Integer val);
  }
