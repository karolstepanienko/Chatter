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

  /**
   * Gets all posts created by user with provided ID.
   * @param creator_id Post author ID.
   * @return All posts created by this user.
   */
  @Query(value = "SELECT * FROM post WHERE creator_id = :creator_id",
  nativeQuery = true)
  Iterable<Post> getPostWithCreatorId(@Param("creator_id") Integer creator_id);

  /**
   * @param privacy
   * @return All posts with provided privacy.
   */
  @Query(
  value = "SELECT * FROM post WHERE privacy = :privacy ORDER BY likes DESC",
  nativeQuery = true)
  Iterable<Post> getPostWithPrivacy(@Param("privacy") String privacy);

  /**
   * @param id Post ID.
   * @return Post with provided ID.
   */
  @Query(value = "SELECT * FROM post WHERE id = :id",
  nativeQuery = true)
  Post getPostWithId(@Param("id") Integer id);

  /**
   * @param user_id Provided user ID.
   * @return All posts that user liked.
   */
  @Query(value = "SELECT post_id FROM user_post WHERE user_id = :user_id",
  nativeQuery = true)
  Iterable<Integer> getLikedPost(@Param("user_id") Integer user_id);

  /**
   * Updates number of likes.
   * @param id Post ID.
   * @param val Number of likes.
   */
  @Modifying
  @Transactional
  @Query(value = "UPDATE post SET likes = :val WHERE id = :id",
  nativeQuery = true)
  void changeLikes(@Param("id") Integer id, @Param("val") Integer val);

  /**
   * Updates relation user <-> liked post.
   * @param id Post ID.
   * @param userList ???
   */
  @Modifying
  @Transactional
  @Query(value = "UPDATE post SET userList = :val WHERE id = :id",
  nativeQuery = true)
  void changeUserList(@Param("id") Integer id,
  @Param("val") Set<User> userList);

  /**
   * ???
   * @param postId
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_post WHERE post_id = :postId",
  nativeQuery = true)
  void deletePostLikes(@Param("postId") Integer postId);

  /**
   * Removes relation between post and user who liked it.
   * @param userId User ID that like the post.
   */
  @Modifying
  @Transactional
  @Query(value = "DELETE FROM user_post WHERE user_id = :userId",
  nativeQuery = true)
  void deleteUserLikes(@Param("userId") Integer userId);
}
