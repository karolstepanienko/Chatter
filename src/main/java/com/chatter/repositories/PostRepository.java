package com.chatter.repositories;

import com.chatter.model.Post.Post;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE creatorId = :creatorId",
    nativeQuery = true)
    Post getPostWithCreatorId(@Param("creatorId") Integer creatorId);

    @Query(value = "SELECT * FROM post WHERE privacy = :privacy",
    nativeQuery = true)
    Iterable<Post> getPostWithPrivacy(@Param("privacy") Integer privacy);
  }
