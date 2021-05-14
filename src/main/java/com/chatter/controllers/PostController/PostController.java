package com.chatter.controllers.PostController;

import java.util.Set;
// Spring-boot imports:
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.chatter.model.Post.Post;
import com.chatter.repositories.PostRepository;
import com.chatter.repositories.UserRepository;
import com.chatter.model.Post.Like;
import com.chatter.model.User.User;

@RestController
@RequestMapping("/api/post")
public class PostController {
  @Autowired  
  private PostRepository postRepository;

  @Autowired  
  private UserRepository userRepository;
    
  @CrossOrigin
  @PostMapping("/addpost")
  public boolean addPost(@RequestBody Post post) {
    User user = this.userRepository.getUserWithId(post.getCreatorId());
    if (user != null) {
      user.addPost(post);
      post.addUser(user);  
      this.postRepository.save(post);
      return true;
    } else return false;
  }

  @GetMapping(path="/allposts")
  public @ResponseBody Iterable<Post> getAllPosts() {
  // This returns a JSON or XML with the posts
    return this.postRepository.getPostWithPrivacy(0) ;
  }

  @CrossOrigin
  @PostMapping(value = "/like", consumes = "application/json", produces = "application/json")
  public String changeLogin(@RequestBody Like like) {
    Integer likesNr = postRepository.getPostWithId(like.post).getLikes();
    postRepository.getPostWithId(like.post).addUser(userRepository.getUserWithId(like.user));
    if (like.status){
      postRepository.getPostWithId(like.post).addUser(userRepository.getUserWithId(like.user));
      postRepository.changeLikes(like.post, likesNr+1);
    }
    else{
      postRepository.getPostWithId(like.post).removeUser(userRepository.getUserWithId(like.user));
      postRepository.changeLikes(like.post, likesNr-1);
    }
    return "like";
  }

  @CrossOrigin
  @GetMapping(path="/likedposts")
  @ResponseBody
  public Iterable<Integer> likedPosts(@RequestParam Integer id) {
    return postRepository.getLikedPost(id);
  }

  @CrossOrigin
  @GetMapping("/get/posts/with/creatorId")
  public @ResponseBody Iterable<Post> getPostsWithCreatorId(@RequestParam Integer creatorId) {
    return this.postRepository.getPostWithCreatorId(creatorId);
  }
}
