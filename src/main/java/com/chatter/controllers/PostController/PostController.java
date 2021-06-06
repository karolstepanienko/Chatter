package com.chatter.controllers.PostController;

// Spring-boot imports:
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;

// Project imports
import com.chatter.model.Post.Post;
import com.chatter.repositories.PostRepository;
import com.chatter.repositories.UserRepository;
import com.chatter.model.Constants.AccountPrivacies;
import com.chatter.model.Post.Like;

@RestController
@RequestMapping("/api/post")
public class PostController {

  /**
   * User repository used to communicate with database.
   * Makes user related changes.
   * */
  @Autowired
  private UserRepository userRepository;

  /**
   * Post repository used to communicate with database.
   * Makes post related changes.
   */
  @Autowired
  private PostRepository postRepository;

  /**
   * @HTTPRequestMethod POST
   * Adds provided post to database.
   * @param post Provided post data.
   * @return True if post saving was successfull.
   */
  @CrossOrigin
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/addpost")
  public boolean addPost(@RequestBody final Post post) {
    this.postRepository.save(post);
    return true;
  }

  /**
   * @HTTPRequestMethod GET
   * Returns all public posts to display them.
   * @return JSON with all public posts.
   */
  @CrossOrigin
  @GetMapping(path = "/allposts")
  public @ResponseBody Iterable<Post> getAllPublicPosts() {
    return this.postRepository.getPostWithPrivacy(
      AccountPrivacies.getPublicAccess());
  }

  /**
   * @HTTPRequestMethod POST
   * Updates like status for given post.
   * @param like Post and like data.
   * @return True if changing number of likes was successfull.
   */
  @CrossOrigin
  @PostMapping(
    value = "/like",
    consumes = "application/json",
    produces = "application/json")
  public boolean updateLikeStatus(@RequestBody final Like like) {
    Integer likesNr = postRepository.getPostWithId(like.getPost()).getLikes();
    if (like.getStatus()) {
        userRepository.getUserWithId(like.getUser()).addPost(
          postRepository.getPostWithId(like.getPost()));
      postRepository.changeLikes(like.getPost(), likesNr + 1);
    } else {
        userRepository.getUserWithId(like.getUser()).removePost(
          postRepository.getPostWithId(like.getPost()));
      postRepository.changeLikes(like.getPost(), likesNr - 1);
    }
    return true;
  }

  /**
   * @HTTPRequestMethod GET
   * @param userId Provided user id.
   * @return All posts ID's liked by a provided user.
   */
  @CrossOrigin
  @GetMapping(path = "/likedposts")
  @ResponseBody
  public Iterable<Integer> userLikedPosts(@RequestParam final Integer userId) {
    return postRepository.getLikedPost(userId);
  }

  /**
   * @HTTPRequestMethod GET
   * @param creatorId Post creator ID.
   * @return All posts created by provided user.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @GetMapping("/get/posts/with/creatorId")
  public @ResponseBody Iterable<Post> getPostsWithCreatorId(
    @RequestParam final Integer creatorId) {
    return this.postRepository.getPostWithCreatorId(creatorId);
  }

  /**
   * @HTTPRequestMethod POST
   * Updates user account privacy.
   * @param postId Provided user ID.
   * @return True if update was succesfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/update/privacy/{postId}")
  public @ResponseBody boolean updatePostPrivacy(
    @PathVariable final Integer postId) {
    Post post = this.postRepository.getPostWithId(postId);
    if (post != null) {
      if (post.getPrivacy().equals(AccountPrivacies.getPublicAccess())) {
        post.setPrivacy(AccountPrivacies.getPrivateAccess());
      } else {
        post.setPrivacy(AccountPrivacies.getPublicAccess());
      }
      this.postRepository.save(post);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @HTTPRequestMethod POST
   * Deletes post with a given ID.
   * @param postId Provided post ID.
   * @return True if delete operation was succesfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/delete/by/Id/{postId}")
  public @ResponseBody boolean deletePost(@PathVariable final Integer postId) {
    Post post = this.postRepository.getPostWithId(postId);
    if (post != null) {
      this.postRepository.deletePostLikes(postId);
      this.postRepository.delete(post);
      return true;
    }
    return false;
  }

  /**
   * @HTTPRequestMethod POST
   * Deletes all posts created during tests.
   * @param userId User ID number.
   * @return True if delete operation was succesfull. False otherwise.
   */
  @CrossOrigin
  @PreAuthorize("hasAuthority('USER') or hasAuthority('MODERATOR') or hasAuthority('ADMIN')")
  @PostMapping("/delete/{userId}")
  public @ResponseBody boolean deletePosts(@PathVariable final Integer userId) {
    this.postRepository.deleteUserPosts(userId);
    return true;

  }
}
