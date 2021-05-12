package com.chatter.controllers.PostController;

// Spring-boot imports:
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.chatter.model.Post.Post;
import com.chatter.repositories.PostRepository;
import com.chatter.model.Post.Like;

@RestController
@RequestMapping("/api")
public class PostController {
  @Autowired  
  private PostRepository postRepository;
    
  @CrossOrigin
  @PostMapping("/addpost")
  public void addUser(@RequestBody Post post) {
      System.out.println(post.toString());
      this.postRepository.save(post);
  }

  @GetMapping(path="/allposts")
  public @ResponseBody Iterable<Post> getAllPosts() {
  // This returns a JSON or XML with the posts
    return this.postRepository.getPostWithPrivacy(0);
  }
  @CrossOrigin
  @PostMapping(value = "/like", consumes = "application/json", produces = "application/json")
  public String changeLogin(@RequestBody Like like) {
    Integer likesNr = postRepository.getPostWithId(like.post).getLikes();
    if (like.status){
      postRepository.changeLikes(like.post, likesNr+1);
    }
    else{
      postRepository.changeLikes(like.post, likesNr-1);
    }
    return "like";
  }
  
    
}
