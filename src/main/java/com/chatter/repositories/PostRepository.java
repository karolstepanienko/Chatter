package com.chatter.repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

// Project imports:
import com.chatter.classes.Post;

public interface PostRepository extends CrudRepository<Post, Integer> {
    @Query(value = "SELECT * FROM post WHERE creatorId = :creatorId",
    nativeQuery = true)
    Post getPostWithCreatorId(@Param("creatorId") Integer creatorId);
  }
